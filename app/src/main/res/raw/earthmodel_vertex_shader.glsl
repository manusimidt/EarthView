
attribute vec3 position;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;
uniform vec3 sunPosition;
uniform vec3 cameraPosition;


varying vec3 unitNormal;
varying vec3 unitToLight;
varying vec3 unitToCamera;


void main(){

    vec4 worldPosition = /*transformationMatrix */ vec4(position,1.0);
    gl_Position = projectionMatrix * viewMatrix * worldPosition;
    unitNormal = normalize(worldPosition.xyz);
    unitToLight = normalize(sunPosition - worldPosition.xyz);
    unitToCamera = normalize(cameraPosition - worldPosition.xyz);

}