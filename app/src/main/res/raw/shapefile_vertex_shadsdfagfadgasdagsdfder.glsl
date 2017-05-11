#version 300 es
     in vec4 position;
     uniform mat4 projectionMatrix;
     uniform mat4 viewMatrix;
     uniform mat4 transformationMatrix;
     uniform bool useprojectionMatrix;
     uniform bool useviewMatrix;
     uniform bool usetransformationMatrix;

     void main(){

        gl_PointSize = 30.0;

        if(useprojectionMatrix && useviewMatrix && usetransformationMatrix){
            gl_Position = projectionMatrix * viewMatrix * transformationMatrix * position;
        }else if(useprojectionMatrix && useviewMatrix){
            gl_Position = projectionMatrix * viewMatrix  * position;
        }else if(useviewMatrix && usetransformationMatrix){
            gl_Position = viewMatrix * transformationMatrix * position;
        }else if(useprojectionMatrix && usetransformationMatrix){
            gl_Position = projectionMatrix * transformationMatrix * position;
        }else if(useprojectionMatrix){
            gl_Position = projectionMatrix * position;
        }else if(useviewMatrix){
            gl_Position = viewMatrix * position;
        }else if(usetransformationMatrix){
            gl_Position = transformationMatrix * position;
        }else{
            gl_Position = position;
        }
        }
