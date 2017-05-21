layout(location = 0) in vec4 position;
layout(location = 2) in vec2 texCoord;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 modelMatrix;


out vec2 textureCoords;

void main(){
    gl_Position =  projectionMatrix * modelMatrix * position;
    textureCoords = texCoord;
}