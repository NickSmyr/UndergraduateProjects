#pragma once
#include "NodeInstance.h"
#include "Objects.h"
#include "Wall.h"
#include <glm/gtc/matrix_transform.hpp>
#include "Beam.h"
#include "Pipe.h"

class Corridor : public NodeInstance
{
public:
	// TODO need extra fields for last coordinate and direction (so we know which way the corr is headed and at what point)
	glm::vec3 pos_offset;
	glm::vec3 direction;
	// If the corridor is straight or right , all children must be rotated around the z axis by this much how do we do this in cpp
	float angleFromZ;

	// todo need prev pos , prev dir as input here
	Corridor(std::vector<GeometryNode*>& objects, OBJECTS type);
	//void add_iris_wall(float position, bool perpendicular);

	Wall * add_wall(float position, float width);
	Wall* add_wall_perpendicular(float position, bool horizontal, float height);
	// Wall with two irises inside
	Wall* add_iris_wall(float position, float width);

	// Wall with one iris and one cannon
	Wall* add_iris_cannon(float position, bool horizontal, bool perpendicular, float width, float height);

	// Adds a cannon to the walls of the corridor
	CannonMount* add_wall_cannon(float position, float degrees);

	// Adds a beam obstacle in the corridor
	Beam* add_beam(float position, float height);

	// Adds a pipe formation in the ceiling of the corridor
	void add_pipe();

	void add_moving_walls(float position, Wall* & w1, Wall* & w2);

	void RecomputeAppModelMatrix();

};