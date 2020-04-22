#ifdef GL_ES
#define LOWP lowp
precision mediump float;
#else
#define LOWP
#endif

const float offset = 16.0 / 128.0;
varying vec2 v_texCoords;
uniform sampler2D u_texture;
void main()
{
	vec4 col = texture2D(u_texture, v_texCoords);
	if (col.a > 0.5)
		gl_FragColor = col;
	else {
		float a = texture2D(u_texture, vec2(v_texCoords.x + offset, v_texCoords.y)).a +
			texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y - offset)).a +
			texture2D(u_texture, vec2(v_texCoords.x - offset, v_texCoords.y)).a +
			texture2D(u_texture, vec2(v_texCoords.x, v_texCoords.y + offset)).a;
		if (col.a < 1.0 && a > 0.0)
			gl_FragColor = vec4(0.0, 0.0, 0.0, 0.8);
		else
			gl_FragColor = col;
	}
}