uniform samplerCube texture0;
uniform vec3 universeIntensity;

in vec3 textureCoords;
out vec4 fragmentColor;

void main(){
    vec4 pixelColor = texture(texture0, textureCoords);
    fragmentColor = pixelColor * vec4(universeIntensity,1.0);
}