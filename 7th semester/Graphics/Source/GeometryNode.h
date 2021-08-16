#ifndef GEOMETRY_NODE_H
#define GEOMETRY_NODE_H

#include <vector>
#include "GLEW\glew.h"
#include <unordered_map>
#include "glm\gtx\hash.hpp"

class GeometryNode
{
public:
	GeometryNode();
	virtual ~GeometryNode();

	virtual void Init(class GeometricMesh* mesh);

	void InitCollisionHull(GeometricMesh* mesh);

	struct Objects
	{
		unsigned int start_offset;
		unsigned int count;

		glm::vec3 diffuse;
		glm::vec3 ambient;
		glm::vec3 specular;

		float shininess;
		GLuint diffuse_textureID;
		GLuint normal_textureID;
		GLuint bump_textureID;
		GLuint emissive_textureID;
		GLuint mask_textureID;
	};

	struct aabb
	{
		glm::vec3 min;
		glm::vec3 max;
		glm::vec3 center;
	};

	std::vector<Objects> parts;

	glm::mat4 model_matrix;
	glm::mat4 app_model_matrix;
	aabb m_aabb;

	GLuint m_vao;
	GLuint m_vbo_positions;
	GLuint m_vbo_normals;
	GLuint m_vbo_tangents;
	GLuint m_vbo_bitangents;
	GLuint m_vbo_texcoords;

	struct triangle { glm::vec3 v0, v1, v2; };

	std::vector<triangle> triangles;
	bool intersectRay(const glm::vec3& pOrigin, const glm::vec3& pDir, const glm::mat4& pWorldMatrix, float& pIsectDist, int32_t& pPrimID, glm::mat4 app_model_matrix , float pTmax = 1.e+15f, float pTmin = 0.f);

	//bool intersectRay(const glm::vec3& pOrigin, const glm::vec3& pDir, const glm::mat4& pWorldMatrix, float& pIsectDist, int32_t& pPrimID, float pTmax = 1.e+15f, float pTmin = 0.f);
	std::string asset_name;
	int node_idx;
};

#endif