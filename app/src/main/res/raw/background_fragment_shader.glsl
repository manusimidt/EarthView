uniform sampler2D texture0;

varying vec2 textureCoords;

void main(){
gl_FragColor = vec4(0.3, 0.3, 0.3, 1.0)*texture2D(texture0, textureCoords);
}