

layout(location = 0) in vec3 position;
layout(location = 1) in vec3 normal;
layout(location = 2) in vec2 texCoord;

uniform mat4 modelMatrix;
uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 sunPosition;

out vec3 surfaceNormal;
out vec2 textureCoords;
out vec3 toSunVector;

vec4 modelToWindowCoordinates(vec4 v, mat4 modelViewPerspectiveMatrix, mat4 viewportTransformationMatrix){
    v = modelViewPerspectiveMatrix * v;                  // clip coordinates
    v.xyz /= v.w;                                                  // normalized device coordinates
    v.xyz = (viewportTransformationMatrix * vec4(v.xyz, 1.0)).xyz; // window coordinates
    return v;
}

void main(){
    textureCoords = texCoord;
    surfaceNormal = normal;
    vec4 worldPosition = projectionMatrix * viewMatrix * modelMatrix * vec4(position,1.0);
    toSunVector = sunPosition - worldPosition.xyz;
    gl_Position = worldPosition;
    //gl_Position = modelToWindowCoordinates(vec4(position, 1.0),viewMatrix,transformationMatrix);
}