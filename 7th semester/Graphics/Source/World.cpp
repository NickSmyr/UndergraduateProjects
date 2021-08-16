#include "World.h"
#include <glm/gtc/matrix_transform.hpp>
#include "Objects.h"
#include "Corridor.h"
#include "Tools.h"

World::World(std::vector<GeometryNode*>& objects)
{
	nodes = std::vector<NodeInstance*>();
	this->objects = &objects;
	last_corridor_coord = glm::vec3(0.f, 0.f, 0.f);
	last_coridor_direction = glm::vec3(0.f, 0.f, -1.f);
}

Corridor * World::add_straight_cor(float rotation_angle)
{
	Corridor* new_node = new Corridor(*this->objects, OBJECTS::CORRIDOR_STRAIGHT);
	
	// Calculating model matrix
	float rotation_radians = glm::radians(rotation_angle);
	glm::mat4 rotation_matrix = glm::rotate(glm::mat4(1.f), rotation_radians, glm::vec3(0, 0, 1));
	new_node->model_matrix = glm::translate(glm::mat4(1.f), last_corridor_coord) * rotation_matrix;
	new_node->app_model_matrix = new_node->model_matrix;

	// Translate center to wcs todo needs to be move in recompute matrices
	new_node->wcs_aabb.center = glm::vec3(new_node->model_matrix * glm::vec4(new_node->lcs_aabb.center, 1.f));

	// Corridors are top level objects
	new_node->parent = nullptr;
	nodes.push_back(new_node);
	// these need to be transformed to wcs
	last_corridor_coord += new_node->pos_offset;
	// this may need to be moved in recompute matrices
	last_coridor_direction += glm::vec3(new_node->model_matrix * glm::vec4(new_node->direction , 1.f));
	return new_node;
}

Corridor * World::add_left_cor(float rotation_angle)
{
	Corridor* new_node = new Corridor(*this->objects, OBJECTS::CORRIDOR_LEFT);
	
	float rotation_radians = glm::radians(rotation_angle);
	glm::mat4 rotation_matrix = glm::rotate(glm::mat4(1.f), rotation_radians, glm::vec3(0, 0, 1));

	new_node->model_matrix = glm::translate(glm::mat4(1.f), last_corridor_coord) * rotation_matrix;
	new_node->app_model_matrix = new_node->model_matrix;
	// Translate center to wcs todo needs to be move in recompute matrices
	new_node->wcs_aabb.center = glm::vec3(new_node->model_matrix * glm::vec4(new_node->lcs_aabb.center, 1.f));

	// Corridors are top level objects
	new_node->parent = nullptr;
	nodes.push_back(new_node);
	// these need to be transformed to wcs
	last_corridor_coord += glm::vec3(rotation_matrix * glm::vec4(new_node->pos_offset, 1.f));
	// this may need to be moved in recompute matrices
	last_coridor_direction += glm::vec3(new_node->model_matrix * glm::vec4(new_node->direction, 1.f));
	return new_node;
}

Corridor * World::add_right_cor(float rotation_angle)
{
	Corridor* new_node = new Corridor(*this->objects, OBJECTS::CORRIDOR_RIGHT);

	float rotation_radians = glm::radians(rotation_angle);
	glm::mat4 rotation_matrix = glm::rotate(glm::mat4(1.f), rotation_radians, glm::vec3(0, 0, 1));

	new_node->model_matrix = glm::translate(glm::mat4(1.f), last_corridor_coord) * rotation_matrix;
	new_node->app_model_matrix = new_node->model_matrix;
	// Translate center to wcs todo needs to be move in recompute matrices
	new_node->wcs_aabb.center = glm::vec3(new_node->model_matrix * glm::vec4(new_node->lcs_aabb.center, 1.f));

	// Corridors are top level objects
	new_node->parent = nullptr;
	nodes.push_back(new_node);
	// these need to be transformed to wcs
	last_corridor_coord += glm::vec3(rotation_matrix * glm::vec4(new_node->pos_offset, 1.f));
	// this may need to be moved in recompute matrices
	last_coridor_direction += glm::vec3(new_node->model_matrix * glm::vec4(new_node->direction, 1.f));
	return new_node;
}

void World::generateRandomCorr()
{
	float p;
	Corridor* new_corridor;
	// generate degree of rotation for new corridor
	float degrees = Tools::mrand() * 180;
	p = Tools::mrand();
	// woth probabilit 1/20 generate a series of rotating corridors 
	if (p <= 1 / 20.f) {
		Wall* w1;
		Wall* w2;

		new_corridor = add_straight_cor(0);
		new_corridor->add_moving_walls(10, w1, w2);
		new_corridor = add_straight_cor(10);
		new_corridor->add_moving_walls(10, w1, w2);
		new_corridor = add_straight_cor(20);
		new_corridor->add_moving_walls(10, w1, w2);
		new_corridor = add_straight_cor(30);
		new_corridor->add_moving_walls(10, w1, w2);
		new_corridor = add_straight_cor(40);
		new_corridor->add_moving_walls(10, w1, w2);
		new_corridor = add_straight_cor(50);
		new_corridor->add_moving_walls(10, w1, w2);
		new_corridor = add_straight_cor(60);
		new_corridor->add_moving_walls(10, w1, w2);
		new_corridor = add_straight_cor(70);
		new_corridor->add_moving_walls(10, w1, w2);
		new_corridor = add_straight_cor(80);
		new_corridor->add_moving_walls(10, w1, w2);
		return;
	}

	p = Tools::mrand();
	// with probability 1 /3 generate straight corridor
	if (p <= 1 / 3.f) {
		new_corridor = add_straight_cor(degrees);
	}
	// with probabiltiy 1/3 generate left corridor
	else if (p <= 2 / 3.f) {
		new_corridor = add_left_cor(degrees);
	}
	// with probability 1/3 generate right corridor
	else {
		new_corridor = add_right_cor(degrees);
	}
	// Each corridor will have at most 2 obstacles (one at z position 10 , one at z postion 20)
	new_corridor->add_pipe();
	// at every position
	for (float position = 10.0; position <= 20.f; position += 10.0f) {
		p = Tools::mrand();
		// With probability x no obstacle is added
		if (p <= no_obstacle_probability) {
			continue;
		}

		p = Tools::mrand();
		//std::cout << "p = " << p << std::endl;
		// With probability 1/7 generate obstacle 1  Iris cannon wall (perpendicular)
		if (p <= (1 / 10.f)) {
			// todo height var
			float height = Tools::mrand() * 6 + 2; // range 2 - 8
			new_corridor->add_iris_cannon(position, true, true, 5, height);
		}
		// With probability 1/7 generate obstacle 2 Iris cannon wall ( vecrtical)
		else if (p <= (2 / 10.f)) {
			float width = Tools::mrand() * 5 - 5; // range  [-5 ,5 ]
			new_corridor->add_iris_cannon(position, true, false, width,0);
		}
		// With probability 1/7 generate obstacle 3 Moving walls
		else if (p <= (2 / 5.f)) {
			Wall* w1;
			Wall* w2;
			new_corridor->add_moving_walls(position, w1, w2);
		}
		// With probability 1/7 generate obstacle 4 Cannon mount on corridor walls
		else if (p <= (3 / 5.f)) {
			// todo need random variable for degrees
			new_corridor->add_wall_cannon(position, 0);
		}
		// With probability 1/7 generate obstacle 5 Iris wall (horzontal)
		else if (p <= (4 / 5.f)) {
			// todo need random variable for height
			float width = Tools::mrand() * 5 - 5; // range  [-5 ,5 ]
			new_corridor->add_iris_wall(position, width);
		}
		// With probability 1/7 generate obstacle 6 Beam
		else if (p <= (5 / 5.f)) {
			float height = Tools::mrand() * 6 + 2; // range 2 - 8
			new_corridor->add_beam(position, height);
		}
	}
}

void World::RecomputeMatrices()
{
	for (NodeInstance* n : nodes) {
		n->RecomputeAppModelMatrix();
	}
}

glm::vec3 World::GetLastCorridorCoordinate()
{
	return last_corridor_coord;
}

void World::SetNoObstacleProbability(float p)
{
	this->no_obstacle_probability = p;
}

float World::GetNoObstacleProbability()
{
	return no_obstacle_probability;
}

void World::CullGeometry(glm::vec3 camera_position, float cull_threshold)
{
	for (int i = 0; i < nodes.size(); i++) {
		NodeInstance* currNode = nodes[i];
		glm::vec3 center = currNode->wcs_aabb.center;
		if (center.z > camera_position.z &&
				glm::distance(camera_position, center) > cull_threshold) {
			// TODO free memory
			nodes.erase(nodes.begin() + i);
		}
	}
}

std::vector<NodeInstance*> World::get_nodes() const
{
	return nodes;
}

std::vector<GeometryNode*> World::get_objects()
{
	return *objects;	
}
