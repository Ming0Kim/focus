<template>
  <div>
    <!-- # 배경사진 -->
    <img
      src="./assets/beams.png"
      alt=""
      class="-z-10 absolute bg-center bg-contain top-1/2 left-1/2 w-screen h-screen -translate-x-1/2 -translate-y-1/2"
    />
    <div
      class="-z-10 absolute inset-0 bg-[url('https://play.tailwindcss.com/img/grid.svg')] bg-center [mask-image:linear-gradient(180deg,white,rgba(255,255,255,0))]"
    ></div>
    <!-- # 배경사진 --><RouterView
    ></RouterView>
  </div>
</template>

<script setup></script>

<style></style>
