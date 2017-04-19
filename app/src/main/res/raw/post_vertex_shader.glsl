

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

vec4 modelToWindowCoordinates(vec4 v, mat4 modelViewPerspectiveMatrix, mat4 viewportTransformationMatrix){
    v = modelViewPerspectiveMatrix * v;                  // clip coordinates
    v.xyz /= v.w;                                                  // normalized device coordinates
    v.xyz = (viewportTransformationMatrix * vec4(v.xyz, 1.0)).xyz; // window coordinates
    return v;
}

void main(){
    textureCoords = texture;
    surfaceNormal = normal;
    vec4 worldPosition = projectionMatrix * viewMatrix * transformationMatrix * vec4(position,1.0);
    toLightVector = lightPosition - worldPosition.xyz;
    gl_Position = worldPosition;
    //gl_Position = modelToWindowCoordinates(vec4(position, 1.0),viewMatrix,transformationMatrix);
}