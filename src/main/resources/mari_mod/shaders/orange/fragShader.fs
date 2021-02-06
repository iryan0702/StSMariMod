//SpriteBatch will use texture unit 0
uniform sampler2D u_texture;

//"in" varyings from our vertex shader
varying vec4 v_color;
varying vec2 v_texCoord;

void main() {
    //sample the texture
    vec4 texColor = texture2D(u_texture, v_texCoord);
    
    float outRed = dot(texColor.rgb, vec3(0.393, 0.769, 0.189));
    float outGreen = dot(texColor.rgb, vec3(0.349, 0.686, 0.168));
    float outBlue = dot(texColor.rgb, vec3(0.272, 0.534, 0.131));

    //final color
    gl_FragColor = vec4(outRed * 0.9, outGreen * 0.7, outBlue * 0.50, texColor.a);
}