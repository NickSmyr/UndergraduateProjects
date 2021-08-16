#pragma once
#include <vector>
#include "GeometryNode.h"
#include "NodeInstance.h"
#include "Corridor.h"
#include <iostream>


class World
{
public:

	World()
	{
	}

	World(std::vector<GeometryNode*>& objects);

	Corridor * add_straight_cor(float rotation_angle);
	Corridor * add_left_cor(float rotation_angle);
	Corridor * add_right_cor(float rotation_angle);

	void generateRandomCorr();

	void RecomputeMatrices();

	glm::vec3 GetLastCorridorCoordinate();
	void SetNoObstacleProbability(float p);
	float GetNoObstacleProbability();

	void CullGeometry(glm::vec3 camera_position, float cull_threshold);

	std::vector<class NodeInstance*> get_nodes() const;
	std::vector<GeometryNode*> get_objects();

	class iterator : public std::iterator<
		std::input_iterator_tag,   // iterator_category
		NodeInstance*,                      // value_type
		long,                      // difference_type
		const NodeInstance**,               // pointer
		NodeInstance*                       // reference
	> {
		// I cannot make visual studio accept the fact that the world object can have a reference to the renderer so i must do this for dfs on scene graph
		const World* w;
		NodeInstance* currentNode;
		std::vector<NodeInstance*> stack;

	// Much needed iterator definition here for dfs
	public:
		explicit iterator(const World* w, bool initialize_empty)
		{
			if (initialize_empty) {
				currentNode = nullptr;
				return;
			}

			stack = std::vector<NodeInstance*>();
			this->w = w;
			if (!w->get_nodes().empty())
			{
				currentNode = w->get_nodes()[0];
			}
			//stack.insert(stack.end(), w->get_nodes().begin(), w->get_nodes().end()); this throws an exception "vector too long and im too tired to fix this"
			for (int i = 0; i < w->get_nodes().size(); i++) {
				stack.push_back(w->get_nodes()[i]);
			}
			if (!stack.empty()) {
				currentNode = stack.back();
				stack.pop_back();
				for (int i = 0; i < currentNode->children.size(); i++) {
					stack.push_back(currentNode->children[i]);
				}
			}

		}
		iterator& operator++()
		{
			if (!stack.empty()) {
				currentNode = stack.back();
				stack.pop_back();
				//std::cout << "Current node children size " << currentNode->children.size() << std::endl;
				for (int i = 0; i < currentNode->children.size(); i++) {
					stack.push_back(currentNode->children[i]);
				}
				
			}
			else {
				currentNode = nullptr;
			}
			return *this;
		}
		iterator operator++(int)
		{
			if (!stack.empty()) {
				//std::cout << "operator pp called 2" << std::endl;
				currentNode = stack.back();
				stack.pop_back();
				//std::cout << "Current node children size " << currentNode->children.size() << std::endl;
				for (int i = 0; i < currentNode->children.size(); i++) {
					stack.push_back(currentNode->children[i]);
				}
				
			}
			else {
				currentNode = nullptr;
			}
			return *this;
		}
		bool operator==(iterator other) const
		{
			return this->currentNode == other.currentNode;
		}
		bool operator!=(iterator other) const
		{
			return this->currentNode != other.currentNode;
		}
		reference operator*() const {
			return this->currentNode;
		}
	};

	iterator begin() { return iterator(this, false); }
	iterator end() { return iterator(this, true);}
	iterator begin() const { return iterator(this, false); }
	iterator end() const { return iterator(this, true); }

protected:
	std::vector<GeometryNode*> * objects;
	std::vector<NodeInstance*> nodes;
	// Variable to hold the coordinate that the next corridor can be placed
	// todo what about forkls ? only for illussion of choice so not important
	glm::vec3 last_corridor_coord;
	glm::vec3 last_coridor_direction;

	float no_obstacle_probability = 0;

};


// member typedefs provided through inheriting from std::iterator
