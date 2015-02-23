#include "geometry.h"
#include <iostream>

bool changed;

Geometry::Geometry() : mFileName(),
    mFilePath(),
    mModelMatrix(),
    mNormalMatrix(),
    mColorBuffer(0),
    mNormalBuffer(0),
    mTexCoordBuffer(0),
    mVertexBuffer(0),
    mVertexArray(0),
    mPrimitiveType(POINTS),
    mVertices(),
    mNormals(),
    mTexCoords(),
    mColors(),
    mTranslation(),
    mScale(1.0f, 1.0f, 1.0f),
    mRotation()

{
    initOpenGLBuffers();

    initDefaultShader();

    mModelMatrix2 = mModelMatrix;
}

Geometry::Geometry(std::string m_FilePath) : mFileName(),
    mFilePath(m_FilePath),
    mModelMatrix(),
    mNormalMatrix(),
    mColorBuffer(0),
    mNormalBuffer(0),
    mTexCoordBuffer(0),
    mVertexBuffer(0),
    mVertexArray(0),
    mPrimitiveType(POINTS),
    mVertices(),
    mNormals(),
    mTexCoords(),
    mColors(),
    mTranslation(),
    mScale(1.0f, 1.0f, 1.0f),
    mRotation()

{
    initOpenGLBuffers();

    initDefaultShader();

    this->parseFile();

    mModelMatrix2 = mModelMatrix;
}

void Geometry::translateX(float m_Translation)
{
    /**
     *  @todo assignment two
     *  add the new translation to the old one
     */
    changed = true;
    mTranslation.x = m_Translation;

    
}

void Geometry::translateY(float m_Translation)
{
    /**
     *  @todo assignment two
     *  add the new translation to the old one
     */
     changed = true;
     mTranslation.y = m_Translation;
}

void Geometry::translateZ(float m_Translation)
{
    /**
     *  @todo assignment two
     *  add the new translation to the old one
     */
     changed = true;
     mTranslation.z = m_Translation;
}

void Geometry::scaleX(float m_ScaleFactor)
{
    /**
     *  @todo assignment two
     *  add the new scale x to the old one
     */
     changed = true;
     mScale.x = m_ScaleFactor;
}

void Geometry::scaleY(float m_ScaleFactor)
{
    /**
     *  @todo assignment two
     *  add the new scale y to the old one
     */
     changed = true;
     mScale.y = m_ScaleFactor;
}

void Geometry::scaleZ(float m_ScaleFactor)
{
    /**
     *  @todo assignment two
     *  add the new scale z to the old one
     */
     changed = true;
     mScale.z = m_ScaleFactor;
}

void Geometry::rotateX(float m_Degrees)
{
    /**
     *  @todo assignment two
     *  add the new rotate x to the old one
     */
     changed = true;
     mRotation.x = m_Degrees;
}

void Geometry::rotateY(float m_Degrees)
{
    /**
     *  @todo assignment two
     *  add the new rotate y to the old one
     */
     changed = true;
     mRotation.y = m_Degrees;
}

void Geometry::rotateZ(float m_Degrees)
{
    /**
     *  @todo assignment two
     *  add the new rotate z to the old one
     */
     changed = true;
     mRotation.z = m_Degrees;
}

void Geometry::createModelMatrix(bool m_UsePostMultiply)
{
    /**
     *  @todo assignment two - will be demonstrated in lab 3.
     *
     * create the model matrix based on the translate, scale and rotation values
     * stored as class members.
     * @param m_UsePostMultiply defines if the single transformation matrices have to be
     * premultiplied or postmultiplied to build the final transformation matrix.
     * store the matrix in mModelMatrix
     */
     if(changed){

        Matrix4x4 translate = Matrix4x4::Translation(mTranslation);
        Matrix4x4 scale = Matrix4x4::Scale(mScale);
        Matrix4x4 rotX = Matrix4x4::RotationX(mRotation.x);
        Matrix4x4 rotY = Matrix4x4::RotationY(mRotation.y);
        Matrix4x4 rotZ = Matrix4x4::RotationZ(mRotation.z);

        if(m_UsePostMultiply){
            mModelMatrix = mModelMatrix2 * scale * rotX * rotY * rotZ * translate;
        }else{
            mModelMatrix = mModelMatrix2 * translate * rotZ * rotY * rotX * scale;
        }
    }
    changed = false;

}
