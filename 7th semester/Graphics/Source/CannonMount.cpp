#include "CannonMount.h"

CannonMount::CannonMount(std::vector<GeometryNode*>& objects) : NodeInstance(objects, OBJECTS::CANNON_MOUNT)
{
}

Cannon * CannonMount::add_left_cannon()
{
	Cannon* c = new Cannon(*objects);
	c->model_matrix = glm::translate(glm::mat4(1.f), glm::vec3(-0.3, -1.3, 0));
	c->parent = this;
	children.push_back(c);
	return c;
}

Cannon* CannonMount::add_right_cannon()
{
	Cannon* c = new Cannon(*objects);
	c->model_matrix = glm::translate(glm::mat4(1.f), glm::vec3(+0.3, -1.3, 0.0));
	c->parent = this;
	children.push_back(c);
	return c;
}
