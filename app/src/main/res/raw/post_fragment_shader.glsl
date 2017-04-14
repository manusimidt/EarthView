uniform sampler2D texture0;
uniform vec3 lightColor;

varying vec3 surfaceNormal;
varying vec3 toLightVector;
varying vec2 textureCoords;

vec3 computeDiffuseLightning(vec3 unitNormal, vec3 unitLightNormal){
    float nDot1 = dot(unitNormal, unitLightNormal);
    float brightness = max(nDot1, 0.4);
    vec3 finalDiffuse = brightness * lightColor;
    return finalDiffuse;
}

void main(){
    vec3 unitNormal = normalize(surfaceNormal);
    vec3 unitLightNormal = normalize(toLightVector);

    gl_FragColor = vec4(computeDiffuseLightning(unitNormal, unitLightNormal),1.0);// * texture2D(texture0, textureCoords);
}
