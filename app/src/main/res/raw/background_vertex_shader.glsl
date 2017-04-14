attribute vec4 position;
attribute vec2 texture;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;


varying vec2 textureCoords;

void main(){
    gl_Position =  projectionMatrix * transformationMatrix * position;
    textureCoords = texture;
}