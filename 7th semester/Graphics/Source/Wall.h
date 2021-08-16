#pragma once
#include "NodeInstance.h"
#include "Objects.h"
#include "Iris.h"
#include "CannonMount.h"

class Wall : public NodeInstance
{
public:
	Wall(std::vector<GeometryNode*>& objects);
	
	// add single cannon on socket
	// add double cannon on socket
	// add iris on socket
	void add_iris_up();
	void add_iris_down();
	void add_cannon_up();
	void add_cannon_down();

}; 
