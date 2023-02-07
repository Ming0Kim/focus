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

            <!-- # 메일함 -->
            <li class="bg-white/60 rounded-lg">
              <span
                class="inline-flex items-center justify-center h-12 w-12 text-2xl text-gray-800"
                ><i class="bx bxs-inbox"></i
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
            <li @click="changeCurrentComp(EvaluatorDashboard)">
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
            <div>
              <li @click="changeCurrentComp(EvaluatorRecord)">
                <a
                  href="#"
                  class="flex flex-row items-center h-12 text-gray-500 transition-transform duration-200 ease-in transform hover:translate-x-2 hover:text-gray-800"
                >
                  <span
                    class="inline-flex items-center justify-center w-12 h-12 text-lg text-gray-400"
                    ><i class="bx bx-id-card"></i
                  ></span>
                  <span class="text-sm font-medium">평가내역</span>
                </a>
              </li>
              <li @click="changeCurrentComp(EvaluatorSetting)">
                <a
                  href="#"
                  class="flex flex-row items-center h-12 text-gray-500 transition-transform duration-200 ease-in transform hover:translate-x-2 hover:text-gray-800"
                >
                  <span
                    class="inline-flex items-center justify-center w-12 h-12 text-lg text-gray-400"
                    ><i class="bx bxs-zap"></i
                  ></span>
                  <span class="text-sm font-medium">환경설정</span>
                </a>
              </li>
            </div>
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

import EvaluatorDashboard from './EvaluatorDashboard.vue';
import EvaluatorRecord from './EvaluatorRecord.vue';
import EvaluatorSetting from './EvaluatorSetting.vue';

const currentComp = shallowRef(EvaluatorDashboard);
const changeCurrentComp = comp => (currentComp.value = comp);
</script>

<style lang="scss" scoped></style>
