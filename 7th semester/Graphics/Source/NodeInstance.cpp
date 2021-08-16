#include "NodeInstance.h"
#include <iostream>


NodeInstance::NodeInstance(std::vector<GeometryNode*>& objects, OBJECTS object)
{
	this->objects = &objects;
	substance = (objects[object]);
	GeometryNode::aabb other = substance->m_aabb;
	children = std::vector<NodeInstance*>();
	this->parent = nullptr;

	this->lcs_aabb.min = other.min;
	this->lcs_aabb.max = other.max;
	this->lcs_aabb.center = other.center;
}

NodeInstance::~NodeInstance()
{
}

GeometryNode* NodeInstance::get_substance()
{
	return this->substance;
}

void NodeInstance::RecomputeAppModelMatrix()
{
	//std::cout << "Recompute app matrix called in  nodeinstance " << std::endl;
	if (parent != nullptr) {
		app_model_matrix = parent->app_model_matrix * model_matrix;
	}
	else {
		//std::cout << "Found one node with parent null " << std::endl;
		app_model_matrix = model_matrix;
	}
	for (NodeInstance* n : children) {
		n->RecomputeAppModelMatrix();
	}
}

void NodeInstance::RecomputeBoundingBox()
{
	//Todo .. //wcs_aabb.min = this->app_model_matrix * lcs_aabb.min;
}

void NodeInstance::InitModelMatrix(glm::mat4 matrix)
{
	backup_model_matrix = matrix;
	model_matrix = matrix;
}

void NodeInstance::ResetModelMatrix()
{
	model_matrix = backup_model_matrix;
}

void NodeInstance::UpdateModelMatrix(glm::mat4 matrix)
{
	model_matrix = matrix;
}
