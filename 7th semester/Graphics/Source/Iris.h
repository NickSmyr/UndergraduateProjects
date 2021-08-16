#pragma once
#include "NodeInstance.h"
#include "Objects.h"
#include "Wall.h"
#include <glm/gtc/matrix_transform.hpp>

class Iris : public NodeInstance
{
public:
	Iris(std::vector<GeometryNode*>& objects) : NodeInstance(objects , OBJECTS::IRIS) {}
};