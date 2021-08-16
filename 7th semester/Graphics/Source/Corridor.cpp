#include "Corridor.h"
#include <iostream>
#include "Tools.h"



Corridor::Corridor(std::vector<GeometryNode*>& objects , OBJECTS type) : NodeInstance(objects, type)
{
	if (type == OBJECTS::CORRIDOR_STRAIGHT) {
		//todo corridor actions 
		pos_offset= glm::vec3(0, 0, -20);
		direction = glm::normalize(pos_offset);
		angleFromZ = 0;
	}
	else if (type == OBJECTS::CORRIDOR_LEFT) {
		//todo corridor actions 
		pos_offset = glm::vec3(-5, 0, -20);
		direction = glm::normalize(pos_offset);
		angleFromZ = -45.f;
	}
	else if (type == OBJECTS::CORRIDOR_RIGHT) {
		//todo corridor actions 
		pos_offset = glm::vec3(+5, 0, -20);
		direction = glm::normalize(pos_offset);
		angleFromZ = +45.f;
	}
	else {
		std::cerr << "Wrong corridor object created" << std::endl;
	}
	// Angle calculation
	float computed = glm::dot(direction, glm::vec3(0, 0, -1));
	computed = glm::acos(computed);
	angleFromZ = glm::degrees(computed);

	// For some reason (i think its acos) we are getting only positive angles
	if (type == OBJECTS::CORRIDOR_RIGHT) {
		angleFromZ *= -1;
	}

}

Wall * Corridor::add_wall(float position, float width)
{
	Wall* w = new Wall(*this->objects);
	// todo refactor this to method ?
	w->parent = this;
	this->children.push_back(w);

	w->model_matrix = glm::translate(glm::mat4(1.f) , glm::vec3(0 , 0 , -1) * position);


	w->model_matrix = glm::translate(glm::mat4(1.f), glm::vec3(width , 0 , 0.f)) * w->model_matrix;
	w->InitModelMatrix(w->model_matrix);
	return w;
}

Wall* Corridor::add_wall_perpendicular(float position, bool horizontal, float height)
{
	Wall* w = new Wall(*this->objects);
	// todo refactor this to method ?
	w->parent = this;
	this->children.push_back(w);

	w->model_matrix = glm::translate(glm::mat4(1.f), glm::vec3(0, 0, -1) * position);

	//if horizontal rotate around z axis
	if (horizontal) {
		w->model_matrix = w->model_matrix * glm::rotate(glm::mat4(1.f), glm::radians(90.f), glm::vec3(0, 0, -1));
	}

	// rotate around y axis to make perpendicular
	w->model_matrix = w->model_matrix * glm::rotate(glm::mat4(1.f), glm::radians(-90.f), glm::vec3(0, 1, 0));

	w->model_matrix = glm::translate(glm::mat4(1.f), glm::vec3(0, -5 + height, 0.f)) * w->model_matrix;
	w->InitModelMatrix(w->model_matrix);
	return w;
}


Wall* Corridor::add_iris_wall(float position, float width)
{
	Wall* w = this->add_wall(position, width);
	w->add_iris_down();
	w->add_iris_up();
	return w;
}

Wall* Corridor::add_iris_cannon(float position, bool horizontal, bool perpendicular,float width , float height)
{
	//std::cout << "adding iris cannon" << std::endl;
	
	Wall* w = nullptr;
	if (perpendicular) {
		w = this->add_wall_perpendicular(position, horizontal , height);
	}
	else {
		w = this->add_wall(position, width);
	}
	w->add_iris_up();
	//std::cout << "adding cnanon" << std::endl;
	w->add_cannon_down();
	return w;
}

CannonMount* Corridor::add_wall_cannon(float position, float degrees)
{
	CannonMount* cm = new CannonMount(*objects);
	children.push_back(cm);
	cm->parent = this;

	cm->add_left_cannon();
	cm->add_right_cannon();

	cm->model_matrix = glm::rotate(glm::mat4(1.f), glm::radians(180.0f), glm::vec3(1.0, 0.0, 0.0));
	// Rotate to look at player
	cm->model_matrix = glm::rotate(glm::mat4(1.f), glm::radians(180.f), glm::vec3(0.0, 1.0, 0.0)) * cm->model_matrix;
	// push to bottom of corridor
	cm->model_matrix = glm::translate(glm::mat4(1.f) ,glm::vec3(0 , -5. , 0) ) * cm->model_matrix;
	//Rotate around the corridors z axis
	cm->model_matrix = glm::rotate(glm::mat4(1.f), glm::radians(degrees), glm::vec3(0.0, 0.0, 1.0)) * cm->model_matrix;
	// Move to desired position across corridor
	cm->model_matrix = glm::translate(glm::mat4(1.f), glm::vec3(0, 0, -position)) * cm->model_matrix;
	
	cm->InitModelMatrix(cm->model_matrix);
	return cm;
}

Beam* Corridor::add_beam(float position, float height)
{
	Beam* b = new Beam(*objects);
	children.push_back(b);
	b->parent = this;

	// Position beam properly in the corridor 
	b->model_matrix = glm::translate(glm::mat4(1.f), glm::vec3(0, -5 + height, -position));
	b->InitModelMatrix(b->model_matrix);
	return b;
}

void Corridor::add_pipe()
{
	Pipe* p1 = new Pipe(*objects);
	children.push_back(p1);
	p1->parent = this;

	float radius = 4.5;
	float angle = glm::radians(180.f - 40.f);
	p1->model_matrix = glm::translate(glm::mat4(1.f), glm::vec3(glm::cos(angle) * radius, glm::sin(angle) * radius, 0.));
	p1->InitModelMatrix(p1->model_matrix);

	Pipe* p2 = new Pipe(*objects);
	children.push_back(p2);
	p2->parent = this;

	radius = 4.5;
	angle = glm::radians(180.f - 40.f);
	p2->model_matrix = glm::translate(glm::mat4(1.f), glm::vec3(glm::cos(angle) * radius, glm::sin(angle) * radius, -10.));
	p2->InitModelMatrix(p2->model_matrix);

	Pipe* p3 = new Pipe(*objects);
	children.push_back(p3);
	p3->parent = this;

	radius = 4.5;
	angle = glm::radians(180.f - 40.f);
	p3->model_matrix = glm::rotate(glm::mat4(1.f), glm::radians(90.f), glm::vec3(0.0, 1.0, 0.0));
	p3->model_matrix = glm::translate(glm::mat4(1.f), glm::vec3(5, glm::sin(angle) * radius, -20.)) * p3->model_matrix;
	p3->InitModelMatrix(p3->model_matrix);
}

void Corridor::add_moving_walls(float position, Wall* & r1, Wall* & r2)
{
	Wall* w1 = new Wall(*this->objects);
	// todo refactor this to method ?
	w1->parent = this;
	this->children.push_back(w1);

	w1->model_matrix = glm::translate(glm::mat4(1.f), glm::vec3(-6.5f, 0.f, -1.f * position) );
	w1->InitModelMatrix(w1->model_matrix);

	Wall* w2 = new Wall(*this->objects);
	// todo refactor this to method ?
	w2->parent = this;
	this->children.push_back(w2);

	w2->model_matrix = glm::translate(glm::mat4(1.f), glm::vec3(+6.5f, 0.f, -1.f * position) );
	w2->InitModelMatrix(w2->model_matrix);
	r1 = w1;
	r2 = w2;
}

void Corridor::RecomputeAppModelMatrix()
{
	//std::cout << "Recompute app matrix called in  corridor " << std::endl;
	// We add a rotation to the model matrices of the children in order for them to adjust properly 
	// this is useful when the corridor is left or right ( in which case we rotate all the obstacles around the y axis)
	glm::mat4 rotation_matrix = glm::rotate(glm::mat4(1.f), glm::radians(angleFromZ), glm::vec3(0.0, 1.0, 0.0));
	for (auto& v : children) {
		v->ResetModelMatrix();
		v->UpdateModelMatrix(rotation_matrix * v->model_matrix);
	}
	NodeInstance::RecomputeAppModelMatrix();
}

