#include "GeometryNode.h"
#include "GeometricMesh.h"
#include <glm/gtc/type_ptr.hpp>
#include "TextureManager.h"
#include <glm/gtx/intersect.hpp>

GeometryNode::GeometryNode()
{
	m_vao = 0;
	m_vbo_positions = 0;
	m_vbo_normals = 0;
	m_vbo_texcoords = 0;
	m_vbo_tangents = 0;
	m_vbo_bitangents = 0;
}

GeometryNode::~GeometryNode()
{
	// delete buffers
	glDeleteVertexArrays(1, &m_vao);
	glDeleteBuffers(1, &m_vbo_positions);
	glDeleteBuffers(1, &m_vbo_normals);
	glDeleteBuffers(1, &m_vbo_tangents);
	glDeleteBuffers(1, &m_vbo_bitangents);
	glDeleteBuffers(1, &m_vbo_texcoords);
}

void GeometryNode::Init(GeometricMesh* mesh)
{
	glGenVertexArrays(1, &m_vao);
	glBindVertexArray(m_vao);

	glGenBuffers(1, &m_vbo_positions);
	glBindBuffer(GL_ARRAY_BUFFER, m_vbo_positions);
	glBufferData(GL_ARRAY_BUFFER, mesh->vertices.size() * sizeof(glm::vec3), &mesh->vertices[0], GL_STATIC_DRAW);

	glEnableVertexAttribArray(0);
	glVertexAttribPointer(
		0,				// attribute index
		3,              // number of elements per vertex, here (x,y,z)
		GL_FLOAT,       // the type of each element
		GL_FALSE,       // take our values as-is
		0,		         // no extra data between each position
		0				// pointer to the C array or an offset to our buffer
	);

	glGenBuffers(1, &m_vbo_normals);
	glBindBuffer(GL_ARRAY_BUFFER, m_vbo_normals);
	glBufferData(GL_ARRAY_BUFFER, mesh->normals.size() * sizeof(glm::vec3), &mesh->normals[0], GL_STATIC_DRAW);

	glEnableVertexAttribArray(1);
	glVertexAttribPointer(
		1,				// attribute index
		3,              // number of elements per vertex, here (x,y,z)
		GL_FLOAT,		// the type of each element
		GL_FALSE,       // take our values as-is
		0,		         // no extra data between each position
		0				// pointer to the C array or an offset to our buffer
	);

	if (!mesh->textureCoord.empty())
	{
		glGenBuffers(1, &m_vbo_texcoords);
		glBindBuffer(GL_ARRAY_BUFFER, m_vbo_texcoords);
		glBufferData(GL_ARRAY_BUFFER, mesh->textureCoord.size() * sizeof(glm::vec2), &mesh->textureCoord[0], GL_STATIC_DRAW);

		glEnableVertexAttribArray(2);
		glVertexAttribPointer(
			2,				// attribute index
			2,              // number of elements per vertex, here (x,y,z)
			GL_FLOAT,		// the type of each element
			GL_FALSE,       // take our values as-is
			0,		         // no extra data between each position
			0				// pointer to the C array or an offset to our buffer
		);
	}

	if (!mesh->tangents.empty())
	{
		glGenBuffers(1, &m_vbo_tangents);
		glBindBuffer(GL_ARRAY_BUFFER, m_vbo_tangents);
		glBufferData(GL_ARRAY_BUFFER, mesh->tangents.size() * sizeof(glm::vec3), &mesh->tangents[0], GL_STATIC_DRAW);

		glEnableVertexAttribArray(3);
		glVertexAttribPointer(
			3,				// attribute index
			3,              // number of elements per vertex, here (x,y,z)
			GL_FLOAT,		// the type of each element
			GL_FALSE,       // take our values as-is
			0,		         // no extra data between each position
			0				// pointer to the C array or an offset to our buffer
		);

		glGenBuffers(1, &m_vbo_tangents);
		glBindBuffer(GL_ARRAY_BUFFER, m_vbo_tangents);
		glBufferData(GL_ARRAY_BUFFER, mesh->tangents.size() * sizeof(glm::vec3), &mesh->tangents[0], GL_STATIC_DRAW);

		glEnableVertexAttribArray(3);
		glVertexAttribPointer(
			3,				// attribute index
			3,              // number of elements per vertex, here (x,y,z)
			GL_FLOAT,		// the type of each element
			GL_FALSE,       // take our values as-is
			0,		         // no extra data between each position
			0				// pointer to the C array or an offset to our buffer
		);

		glGenBuffers(1, &m_vbo_bitangents);
		glBindBuffer(GL_ARRAY_BUFFER, m_vbo_bitangents);
		glBufferData(GL_ARRAY_BUFFER, mesh->bitangents.size() * sizeof(glm::vec3), &mesh->bitangents[0], GL_STATIC_DRAW);

		glEnableVertexAttribArray(4);
		glVertexAttribPointer(
			4,				// attribute index
			3,              // number of elements per vertex, here (x,y,z)
			GL_FLOAT,		// the type of each element
			GL_FALSE,       // take our values as-is
			0,		         // no extra data between each position
			0				// pointer to the C array or an offset to our buffer
		);
	}

	glBindVertexArray(0);
	glBindBuffer(GL_ARRAY_BUFFER, 0);

	// *********************************************************************

	for (int i = 0; i < mesh->objects.size(); i++)
	{
		Objects part;
		part.start_offset = mesh->objects[i].start;
		part.count = mesh->objects[i].end - mesh->objects[i].start;
		auto material = mesh->materials[mesh->objects[i].material_id];
		
		part.diffuse = glm::vec4(material.diffuse[0], material.diffuse[1], material.diffuse[2], 1.0);
		part.specular = glm::vec3(material.specular[0], material.specular[1], material.specular[2]);
		part.ambient = glm::vec3(material.ambient[0], material.ambient[1], material.ambient[2]);

		part.shininess = material.shininess;
		part.diffuse_textureID = (material.textureDiffuse.empty())? 0 : TextureManager::GetInstance().RequestTexture(material.textureDiffuse.c_str());
		part.mask_textureID = (material.textureSpecular.empty()) ? 0 : TextureManager::GetInstance().RequestTexture(material.textureSpecular.c_str());
		part.emissive_textureID = (material.textureAmbient.empty()) ? 0 : TextureManager::GetInstance().RequestTexture(material.textureAmbient.c_str());
		part.normal_textureID = (material.textureNormal.empty())? 0 : TextureManager::GetInstance().RequestTexture(material.textureNormal.c_str());
		part.bump_textureID = (material.textureBump.empty()) ? 0 : TextureManager::GetInstance().RequestTexture(material.textureBump.c_str());

		parts.push_back(part);
	}

	this->m_aabb.min = glm::vec3(std::numeric_limits<float_t>::max());
	this->m_aabb.max = glm::vec3(-std::numeric_limits<float_t>::max());

	for (auto& v : mesh->vertices)
	{
		this->m_aabb.min = glm::min(this->m_aabb.min, v);
		this->m_aabb.max = glm::max(this->m_aabb.max, v);
	}

	this->m_aabb.center = (this->m_aabb.min + this->m_aabb.max) * 0.5f;
}

void GeometryNode::InitCollisionHull(class GeometricMesh* mesh)
{
	this->triangles.resize(mesh->vertices.size() / 3);

	for (size_t t = 0; t < this->triangles.size(); ++t)
	{
		this->triangles[t].v0 = mesh->vertices[t * 3 + 0];
		this->triangles[t].v1 = mesh->vertices[t * 3 + 1];
		this->triangles[t].v2 = mesh->vertices[t * 3 + 2];
	}
}


bool GeometryNode::intersectRay(
	const glm::vec3& pOrigin_wcs,
	const glm::vec3& pDir_wcs,
	const glm::mat4& pWorldMatrix,
	float& pIsectDist,
	int32_t& pPrimID,
	glm::mat4 app_model_matrix,
	float pTmax,
	float pTmin)
{
	if (pTmax < pTmin || glm::length(pDir_wcs) < glm::epsilon<float>()) return false;

	const glm::vec3 normDir = glm::normalize(pDir_wcs);

	const glm::mat4 wordToModel = glm::inverse(pWorldMatrix * app_model_matrix);
	const glm::vec3 o_local = wordToModel * glm::vec4(pOrigin_wcs, 1.f);
	const glm::vec3 d_local = glm::normalize(glm::vec3(wordToModel * glm::vec4(normDir, 0.f)));

	pIsectDist = pTmax;
	float_t curMin = pTmax;
	bool found_isect = false;
	glm::vec3 isect(1.f);
	pPrimID = -1;

	for (uint32_t i = 0; i < this->triangles.size(); ++i)
	{
		auto& tr = this->triangles[i];
		glm::vec3 barycoord;

		if (glm::intersectRayTriangle(o_local, d_local, tr.v0, tr.v1, tr.v2, barycoord))
		{
			const glm::vec3 tmp_isect = tr.v0 * barycoord.x + tr.v1 * barycoord.y + tr.v2 * barycoord.z;
			float dist = glm::distance(o_local, tmp_isect);

			if (dist < curMin && dist >= pTmin)
			{
				curMin = dist;
				found_isect = true;
				isect = tmp_isect;
				pPrimID = i;
			}
		}
	}

	if (found_isect)
	{
		glm::vec3 isec_wcs = pWorldMatrix * app_model_matrix * glm::vec4(isect, 1.f);
		pIsectDist = glm::distance(pOrigin_wcs, isec_wcs);
	}

	return found_isect;
}
