precision mediump float;
varying vec4 vColor;
uniform float ourColor;

void main() {
    gl_FragColor=vec4(vColor.x+ourColor ,vColor.y+ourColor,vColor.z+ourColor,1.0);
}