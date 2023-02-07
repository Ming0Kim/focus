<template>
  <div>
    <!--     
    # HEADER
  -->
    <nav class="bg-white/50 border-gray-200 px-2 sm:px-4 py-2.5 rounded">
      <div
        class="container flex flex-wrap items-center justify-between mx-auto"
      >
        <div class="flex items-center space-x-2">
          <img class="h-7" src="../assets/blue_opacity.png" alt="logo" />
          <span class="self-center text-xl font-semibold whitespace-nowrap"
            >Focus</span
          >
        </div>
        <div class="hidden w-full md:block md:w-auto" id="navbar-default">
          <ul
            class="flex flex-col items-center p-4 mt-4 border border-gray-100 rounded-lg md:flex-row md:space-x-8 md:mt-0 md:text-sm md:font-medium md:border-0"
          >
            <!-- # FAQ -->
            <li class="bg-white/60 rounded-lg">
              <span
                class="inline-flex items-center justify-center h-12 w-12 text-2xl text-gray-800"
                ><i class="bx bx-question-mark"></i
              ></span>
            </li>
            <!-- # 메일보내기 -->
            <li class="bg-white/60 rounded-lg">
              <span
                class="inline-flex items-center justify-center h-12 w-12 text-2xl text-gray-800"
                ><i class="bx bx-paper-plane"></i
              ></span>
            </li>
            <!-- # 메일함 -->
            <li class="bg-white/60 rounded-lg">
              <span
                class="inline-flex items-center justify-center h-12 w-12 text-2xl text-gray-800"
                ><i class="bx bxs-inbox"></i
              ></span>
            </li>
            <!-- # 종 -->
            <li class="bg-white/60 rounded-lg">
              <span
                class="inline-flex items-center justify-center h-12 w-12 text-2xl text-gray-800"
                ><i class="bx bx-bell"></i
              ></span>
            </li>
            <!-- 로그아웃 -->
            <li class="bg-white/60 rounded-lg" @click="logout()">
              <span
                class="inline-flex items-center justify-center h-12 w-12 text-2xl text-gray-800"
                ><i class="bx bx-heart"></i
              ></span>
            </li>
            <!-- # 프로필 -->
            <div class="flex items-center space-x-2">
              <img
                class="h-8 rounded-full"
                src="http://www.gravatar.com/avatar/2acfb745ecf9d4dccb3364752d17f65f?s=260&d=mp"
                alt="James Bhatta"
              />

              <h4
                class="font-semibold text-gray-700 capitalize font-poppins tracking-wide"
              >
                James Smith
              </h4>
            </div>
          </ul>
        </div>
      </div>
    </nav>
    <div class="flex">
      <!-- 
      # NAVBAR
      -->
      <div class="flex flex-none min-h-screen">
        <div class="flex flex-col w-56 overflow-hidden bg-white/50">
          <ul class="flex flex-col py-4 divide-y space-y-60">
            <div>
              <li @click="changeCurrentComp(ServiceDashboard)">
                <a
                  href="#"
                  class="flex flex-row items-center h-12 text-gray-500 transition-transform duration-200 ease-in transform hover:translate-x-2 hover:text-gray-800"
                >
                  <span
                    class="inline-flex items-center justify-center w-12 h-12 text-lg text-gray-400"
                    ><i class="bx bxs-dashboard"></i
                  ></span>
                  <span class="text-sm font-medium">홈 / 대시보드</span>
                </a>
              </li>
              <li @click="changeCurrentComp(ServiceAccount)">
                <a
                  href="#"
                  class="flex flex-row items-center h-12 text-gray-500 transition-transform duration-200 ease-in transform hover:translate-x-2 hover:text-gray-800"
                >
                  <span
                    class="inline-flex items-center justify-center w-12 h-12 text-lg text-gray-400"
                    ><i class="bx bx-id-card"></i
                  ></span>
                  <span class="text-sm font-medium">계정관리</span>
                </a>
              </li>
            </div>
            <li @click="changeCurrentComp(ServiceNotice)">
              <a
                href="#"
                class="flex flex-row items-center h-12 text-gray-500 transition-transform duration-200 ease-in transform hover:translate-x-2 hover:text-gray-800"
              >
                <span
                  class="inline-flex items-center justify-center w-12 h-12 text-lg text-gray-400"
                  ><i class="bx bxs-megaphone"></i
                ></span>
                <span class="text-sm font-medium">공지기능</span>
              </a>
            </li>
          </ul>
        </div>
      </div>
      <!-- 
      # DynamicComponentContents
      -->
      <!-- <div>
        <h2>chocolate yummy</h2>
      </div> -->
      <component class="flex-auto" :is="currentComp" />
    </div>
  </div>
</template>

<script setup>
import { ref, shallowRef } from 'vue';
import { useRouter } from 'vue-router';
import { useStore } from 'vuex';

import ServiceAccount from '@/views/ServiceAccount.vue';
import ServiceDashboard from '@/views/ServiceDashboard.vue';
import ServiceNotice from './ServiceNotice.vue';

const currentComp = shallowRef(ServiceDashboard);
const changeCurrentComp = comp => (currentComp.value = comp);

const router = useRouter();
const store = useStore();

const logout = () => {
  store.dispatch('logout').then(() => {
    router.push({ name: 'Login' });
  });
};
</script>

<style lang="scss" scoped></style>
