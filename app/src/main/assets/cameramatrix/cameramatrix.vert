attribute vec4 vPosition;
uniform mat4 uMVPMatrix;
uniform mat4 uCameraMatrix;
varying  vec4 vColor;
attribute vec4 aColor;

void main() {
      vColor=aColor;
      gl_Position = uMVPMatrix *vPosition;
   // gl_Position = vPosition;
}