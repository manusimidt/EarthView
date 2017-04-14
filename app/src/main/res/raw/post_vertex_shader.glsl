

attribute vec3 position;
attribute vec3 normal;
attribute vec2 texture;

uniform mat4 transformationMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition;

varying vec3 surfaceNormal;
varying vec2 textureCoords;
varying vec3 toLightVector;


void main(){
     textureCoords = texture;
    surfaceNormal = normal;
    vec4 worldPosition = projectionMatrix * viewMatrix * transformationMatrix * vec4(position,1.0);
     toLightVector = lightPosition - worldPosition.xyz;
    gl_Position = worldPosition;
}