precision mediump float;
uniform vec4 vColor;
uniform float ourColor;

void main() {
    gl_FragColor=vec4(vColor.x ,vColor.y,vColor.z,1.0);
}