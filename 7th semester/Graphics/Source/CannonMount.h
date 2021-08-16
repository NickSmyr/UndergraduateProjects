#pragma once

#include "NodeInstance.h"
#include "Objects.h"
#include "Iris.h"
#include "Cannon.h"


class CannonMount : public NodeInstance
{
public:
	// Todo maybe this needs to support turning around the cannons and also rotatinf the cannon mount
	CannonMount(std::vector<GeometryNode*>& objects);

	Cannon * add_left_cannon();
	Cannon * add_right_cannon();
	
};
