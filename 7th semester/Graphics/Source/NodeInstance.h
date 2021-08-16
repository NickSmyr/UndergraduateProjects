#pragma once
#include <glm/detail/type_vec.hpp>
#include "GeometryNode.h"
#include "Objects.h"


class NodeInstance
{
public:
	struct aabb
	{
		glm::vec3 min;
		glm::vec3 max;
		glm::vec3 center;
	};
	NodeInstance(std::vector<GeometryNode*> & objects , OBJECTS object);

	virtual ~NodeInstance();

	// Need to have a reference of all the objects if we need to create more
	std::vector<GeometryNode*> * objects;
	
	// Need to keep reference to the children how we gon draw them huh ?
	std::vector<NodeInstance*> children;
	// Need a reference to parent s.t we can compute the app_model_matrix at each node
	NodeInstance* parent;


	GeometryNode* substance;

	glm::mat4 model_matrix;
	// This is useful in case we need to reconstruct the initial model matrix
	glm::mat4 backup_model_matrix;
	glm::mat4 app_model_matrix;

	// TODO wcs aabb and lcs aabb 
	// lcs aabb is fixed while wcs aabb changes whenever our model matrix changes (or parent model matrix)
	aabb wcs_aabb;
	aabb lcs_aabb;

	GeometryNode* get_substance();
	// These functions should be called whenever a parent/ grandparent node has a change to model matrix
	virtual void RecomputeAppModelMatrix();
	void RecomputeBoundingBox();


	void InitModelMatrix(glm::mat4 matrix);
	void ResetModelMatrix();
	void UpdateModelMatrix(glm::mat4 matrix);


	
};