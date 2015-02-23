#include "basicopenglview.h"
#include <QMessageBox>
#include <QtCore>
#include <iostream>
#include <fstream>
#include <sstream>

#ifdef __APPLE__
#include <OpenGL/gl3.h>
#else
#define GLEW_STATIC
#include <GL/glew.h>
#endif

#include <QMouseEvent>

void BasicOpenGLView::loadMaterial(std::string m_Material)
{
}

void BasicOpenGLView::setOrtho(bool m_RenderOrtho)
{
    mRenderOrtho = m_RenderOrtho;

    this->resizeGL(this->width(), this->height());
}

void BasicOpenGLView::setPostMultiply(bool m_PostMultiply)
{
    mUsePostMultiply = m_PostMultiply;
}

void BasicOpenGLView::initializeGL()
{
#ifndef __APPLE__
    GLenum err = glewInit();
    if (GLEW_OK != err)
    {
        std::cerr << "Glew could not initialize" << std::endl;
    }
#endif
    glClearColor(1.0f, 1.0f, 1.0f, 1.0f);
#if QT_VERSION < QT_VERSION_CHECK(5,0,0)
    std::cout<<glGetString(GL_VERSION)<<std::endl;
#else
    if(this->format().majorVersion() < 3 && this->format().minorVersion() < 2)
    {
        QMessageBox msgBox;
        msgBox.setText("The OpenGL version available on this computer is too old.");
        msgBox.setInformativeText("OpenGL 3.2 or higher required to run this application.");
        msgBox.setIcon(QMessageBox::Critical);
        msgBox.setStandardButtons(QMessageBox::Close);
        msgBox.setDefaultButton(QMessageBox::Close);
        msgBox.exec();
        QCoreApplication::quit();
    }
#endif

    mLightPos = Vector3(0.0, 10.0, 0.0);
    mLightCol = Vector4(1.0, 1.0, 1.0, 1.0);

    glEnable(GL_CULL_FACE);
    glEnable(GL_DEPTH_TEST);

}

void BasicOpenGLView::resizeGL(int width, int height)
{
    glViewport(0, 0, width, height);
    glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);

}

void BasicOpenGLView::mousePressEvent(QMouseEvent *event)
{
	mFirstFrameDragging = true;
}

void BasicOpenGLView::mouseReleaseEvent(QMouseEvent *event)
{
    mFirstFrameDragging = true;
}

void BasicOpenGLView::mouseMoveEvent(QMouseEvent *event)
{
	float eventX = (float)event->x() / width();
    float eventY = 1.0f - (float) event->y() / height();
	
    eventX -= 0.5f;
    eventY -= 0.5f;
    eventX *= 2.0f;
    eventY *= 2.0f;
	
	Vector3 curPoint(eventX, eventY);
	
	if(mFirstFrameDragging)
	{
		mLastMousePos = curPoint;
		mFirstFrameDragging = false;
		return;
	}
	
	Vector3 difference;
	for(size_t i = 0; i < 3; ++i)
	{
		difference.elements[i] = curPoint.elements[i] - mLastMousePos.elements[i];
	}

    Matrix4x4 rotationX = Matrix4x4::RotationX(-difference.elements[1]);
    Matrix4x4 rotationY = Matrix4x4::RotationY(difference.elements[0]);

    Vector3 oldForward(mViewMatrix[2], mViewMatrix[6], mViewMatrix[10]);
    oldForward.normalize();
    oldForward *= -mDistance;

    Matrix4x4 translateToOrigin = Matrix4x4::Translation(oldForward);
    mViewMatrix = mViewMatrix * translateToOrigin;

    mViewMatrix = (mViewMatrix * rotationX) * rotationY;

    Vector3 newForward(mViewMatrix[2], mViewMatrix[6], mViewMatrix[10]);
    newForward.normalize();
    newForward *= mDistance;

    Matrix4x4 translateFromOrigin = Matrix4x4::Translation(newForward);

    mViewMatrix = mViewMatrix * translateFromOrigin;

    mLastMousePos = curPoint;
}

void BasicOpenGLView::keyPressEvent(QKeyEvent *event)
{
}

void BasicOpenGLView::keyReleaseEvent(QKeyEvent *event)
{
}

void BasicOpenGLView::wheelEvent(QWheelEvent * e)
{
    int delta = e->delta();

    Vector3 oldForward(mViewMatrix[2], mViewMatrix[6], mViewMatrix[10]);
    oldForward.normalize();
    oldForward *= -mDistance;

    Matrix4x4 translateToOrigin = Matrix4x4::Translation(oldForward);
    mViewMatrix = mViewMatrix * translateToOrigin;

    mDistance -= ((float)delta) / 10.0f;

    Vector3 newForward(mViewMatrix[2], mViewMatrix[6], mViewMatrix[10]);
    newForward.normalize();
    newForward *= mDistance;

    Matrix4x4 translateFromOrigin = Matrix4x4::Translation(newForward);

    mViewMatrix = mViewMatrix * translateFromOrigin;
}

void BasicOpenGLView::translateX(float m_Value, std::string m_ModelName)
{
    if(mGeometries.find(m_ModelName) != mGeometries.end())
    {
        mGeometries[m_ModelName]->translateX(m_Value);
    }
}

void BasicOpenGLView::translateY(float m_Value, std::string m_ModelName)
{
    if(mGeometries.find(m_ModelName) != mGeometries.end())
    {
        mGeometries[m_ModelName]->translateY(m_Value);
    }
}

void BasicOpenGLView::translateZ(float m_Value, std::string m_ModelName)
{
    if(mGeometries.find(m_ModelName) != mGeometries.end())
    {
        mGeometries[m_ModelName]->translateZ(m_Value);
    }
}

void BasicOpenGLView::rotateX(float m_Value, std::string m_ModelName)
{
    if(mGeometries.find(m_ModelName) != mGeometries.end())
    {
        mGeometries[m_ModelName]->rotateX(m_Value);
    }
}

void BasicOpenGLView::rotateY(float m_Value, std::string m_ModelName)
{
    if(mGeometries.find(m_ModelName) != mGeometries.end())
    {
        mGeometries[m_ModelName]->rotateY(m_Value);
    }
}

void BasicOpenGLView::rotateZ(float m_Value, std::string m_ModelName)
{
    if(mGeometries.find(m_ModelName) != mGeometries.end())
    {
        mGeometries[m_ModelName]->rotateZ(m_Value);
    }
}

void BasicOpenGLView::scaleX(float m_Value, std::string m_ModelName)
{
    if(mGeometries.find(m_ModelName) != mGeometries.end())
    {
        mGeometries[m_ModelName]->scaleX(m_Value);
    }
}

void BasicOpenGLView::scaleY(float m_Value, std::string m_ModelName)
{
    if(mGeometries.find(m_ModelName) != mGeometries.end())
    {
        mGeometries[m_ModelName]->scaleY(m_Value);
    }
}

void BasicOpenGLView::scaleZ(float m_Value, std::string m_ModelName)
{
    if(mGeometries.find(m_ModelName) != mGeometries.end())
    {
        mGeometries[m_ModelName]->scaleZ(m_Value);
    }
}




