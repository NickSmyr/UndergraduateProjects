#include "Wall.h"

Wall::Wall(std::vector<GeometryNode*>& objects) : NodeInstance(objects, OBJECTS::WALL)
{
}

void Wall::add_iris_up()
{
	Iris* iris = new Iris(*objects);
	children.push_back(iris);
	iris->parent = this;

	iris->model_matrix = glm::translate(glm::mat4(1.f), glm::vec3(0, 3, 0));
}

void Wall::add_iris_down()
{
	Iris* iris = new Iris(*objects);
	children.push_back(iris);
	iris->parent = this;

	iris->model_matrix = glm::translate(glm::mat4(1.f), glm::vec3(0, -3, 0));
}

void Wall::add_cannon_up()
{
	CannonMount* cm = new CannonMount(*objects);
	children.push_back(cm);
	cm->parent = this;

	cm->add_left_cannon();
	cm->add_right_cannon();
	cm->model_matrix = 
		glm::translate(glm::mat4(1.f), glm::vec3(0.0, 3.0, 0.0)) *
		glm::rotate(glm::mat4(1.f), glm::radians(90.f), glm::vec3(0, 1, 0));
}

void Wall::add_cannon_down()
{
	CannonMount* cm = new CannonMount(*objects);
	children.push_back(cm);
	cm->parent = this;

	cm->add_left_cannon();
	cm->add_right_cannon();
	cm->model_matrix =
		glm::translate(glm::mat4(1.f), glm::vec3(0.0, -3.0, 0.0)) *
		glm::rotate(glm::mat4(1.f), glm::radians(-90.f), glm::vec3(1, 0, 0));
}