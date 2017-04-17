
varying vec3 unitNormal;
varying vec3 unitToLight;
varying vec3 unitToCamera;

uniform vec3 lightColor;
uniform sampler2D texture0; //Day Texture
uniform sampler2D texture1; //NightTexture
uniform float enableFullLightning;



const float blendDuration = 0.1;
const float blendDurationScale = 5.0; // blendDurationScale = 1/(2*blendDuration);


const vec4 og_diffuseSpecularAmbientShininess = vec4(0.2,0.2,0.2,1);


    float lightIntensity(vec3 normal, vec3 toLight, vec3 toEye, float diffuseDot, vec4 diffuseSpecularAmbientShininess){
        vec3 toReflectedLight = reflect(-toLight, normal);

        float diffuse = max(diffuseDot, 0.0);
        float specular = max(dot(toReflectedLight, toEye), 0.0);
        specular = pow(specular, diffuseSpecularAmbientShininess.w);

        return (diffuseSpecularAmbientShininess.x * diffuse) +
               (diffuseSpecularAmbientShininess.y * specular) +
                diffuseSpecularAmbientShininess.z;
    }

    vec2 computeTextureCoordinates(vec3 normal)
    {
        return vec2(atan(normal.y, normal.x) * og_oneOverTwoPi + 0.5, asin(normal.z) * og_oneOverPi + 0.5);
    }

    vec4 nightColor(vec3 normal)
    {
        return texture2D(texture1, computeTextureCoordinates(normal));
    }

    vec4 dayColor(vec3 normal, vec3 toLight, vec3 toEye, float diffuseDot, vec4 diffuseSpecularAmbientShininess)
    {
        float intensity = lightIntensity(normal, toLight, toEye, diffuseDot, diffuseSpecularAmbientShininess);
        return intensity * texture2D(texture0, computeTextureCoordinates(normal));
    }

    void main()
    {

        float diffuse = dot(unitToLight, unitNormal);


        if(enableFullLightning >0.5){
                gl_FragColor = texture2D(texture0, computeTextureCoordinates(unitNormal));
        }else{
            if (diffuse > blendDuration)
            {
                gl_FragColor = dayColor(unitNormal, unitToLight, unitToCamera, diffuse, og_diffuseSpecularAmbientShininess);
            }
            else if (diffuse < -blendDuration)
            {
                gl_FragColor = nightColor(unitNormal);
            }
            else
            {
                vec4 night = nightColor(unitNormal);
                vec4 day = dayColor(unitNormal, unitToLight, unitToCamera, diffuse, og_diffuseSpecularAmbientShininess);
                gl_FragColor = mix(night, day, (diffuse + blendDuration) * blendDurationScale);
            }
        }
        }
