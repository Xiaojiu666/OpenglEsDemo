attribute vec4 vPosition;
attribute vec4 zColor;
varying  vec4 vColor;

void main() {
    vColor = zColor;
    gl_Position = vPosition;
}