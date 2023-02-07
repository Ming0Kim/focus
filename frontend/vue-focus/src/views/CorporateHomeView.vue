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
            <li class="bg-white/60 rounded-lg">
              <span
                class="inline-flex items-center justify-center h-12 w-12 text-2xl text-gray-800"
                ><i class="bx bx-conversation"></i
              ></span>
            </li>
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
            <!-- # 종 -->
            <li class="bg-white/60 rounded-lg">
              <span
                class="inline-flex items-center justify-center h-12 w-12 text-2xl text-gray-800"
                ><i class="bx bx-bell"></i
              ></span>
            </li>
            <!-- # 프로필 -->
            <button
              class="flex items-center space-x-2"
              @click="changeCurrentComp(CorporateProfile)"
            >
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
            </button>
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
          <ul class="flex flex-col py-4 divide-y space-y-20">
            <div>
              <li @click="changeCurrentComp(CorporateDashboard)">
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
            </div>
            <div>
              <li @click="changeCurrentComp(CorporateManageEvaluator)">
                <a
                  href="#"
                  class="flex flex-row items-center h-12 text-gray-500 transition-transform duration-200 ease-in transform hover:translate-x-2 hover:text-gray-800"
                >
                  <span
                    class="inline-flex items-center justify-center w-12 h-12 text-lg text-gray-400"
                    ><i class="bx bxs-user-voice"></i
                  ></span>
                  <span class="text-sm font-medium">1 평가자 관리</span>
                </a>
              </li>
              <li @click="changeCurrentComp(CorporateManageProcess)">
                <a
                  href="#"
                  class="flex flex-row items-center h-12 text-gray-500 transition-transform duration-200 ease-in transform hover:translate-x-2 hover:text-gray-800"
                >
                  <span
                    class="inline-flex items-center justify-center w-12 h-12 text-lg text-gray-400"
                    ><i class="bx bx-list-ol"></i
                  ></span>
                  <span class="text-sm font-medium">2 프로세스 관리</span>
                </a>
              </li>
              <li @click="changeCurrentComp(CorporateManageApplicant)">
                <a
                  href="#"
                  class="flex flex-row items-center h-12 text-gray-500 transition-transform duration-200 ease-in transform hover:translate-x-2 hover:text-gray-800"
                >
                  <span
                    class="inline-flex items-center justify-center w-12 h-12 text-lg text-gray-400"
                    ><i class="bx bxs-user-detail"></i
                  ></span>
                  <span class="text-sm font-medium">3 지원자 관리</span>
                </a>
              </li>
              <li @click="changeCurrentComp(CorporateManageInterview)">
                <a
                  href="#"
                  class="flex flex-row items-center h-12 text-gray-500 transition-transform duration-200 ease-in transform hover:translate-x-2 hover:text-gray-800"
                >
                  <span
                    class="inline-flex items-center justify-center w-12 h-12 text-lg text-gray-400"
                    ><i class="bx bx-calendar"></i
                  ></span>
                  <span class="text-sm font-medium">4 면접일정 관리</span>
                </a>
              </li>
              <li @click="changeCurrentComp(CorporateManagePass)">
                <a
                  href="#"
                  class="flex flex-row items-center h-12 text-gray-500 transition-transform duration-200 ease-in transform hover:translate-x-2 hover:text-gray-800"
                >
                  <span
                    class="inline-flex items-center justify-center w-12 h-12 text-lg text-gray-400"
                    ><i class="bx bxs-user-plus"></i
                  ></span>
                  <span class="text-sm font-medium">5 합격자 관리</span>
                </a>
              </li>
            </div>
            <div>
              <li @click="changeCurrentComp(CorporateSheet)">
                <a
                  href="#"
                  class="flex flex-row items-center h-12 text-gray-500 transition-transform duration-200 ease-in transform hover:translate-x-2 hover:text-gray-800"
                >
                  <span
                    class="inline-flex items-center justify-center w-12 h-12 text-lg text-gray-400"
                    ><i class="bx bx-spreadsheet"></i
                  ></span>
                  <span class="text-sm font-medium">평가지 관리</span>
                </a>
              </li>
              <li @click="changeCurrentComp(CorporateStatics)">
                <a
                  href="#"
                  class="flex flex-row items-center h-12 text-gray-500 transition-transform duration-200 ease-in transform hover:translate-x-2 hover:text-gray-800"
                >
                  <span
                    class="inline-flex items-center justify-center w-12 h-12 text-lg text-gray-400"
                    ><i class="bx bxs-doughnut-chart"></i
                  ></span>
                  <span class="text-sm font-medium">합격자 통계 조회</span>
                </a>
              </li>
              <li @click="changeCurrentComp(CorporateNotice)">
                <a
                  href="#"
                  class="flex flex-row items-center h-12 text-gray-500 transition-transform duration-200 ease-in transform hover:translate-x-2 hover:text-gray-800"
                >
                  <span
                    class="inline-flex items-center justify-center w-12 h-12 text-lg text-gray-400"
                    ><i class="bx bxs-megaphone"></i
                  ></span>
                  <span class="text-sm font-medium">공지게시판</span>
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

import CorporateDashboard from './CoporateDashboard.vue';
import CorporateManageEvaluator from './CorporateManageEvaluator.vue';
import CorporateManageProcess from './CorporateManageProcess.vue';
import CorporateManageApplicant from './CorporateManageApplicant.vue';
import CorporateManageInterview from './CorporateManageInterview.vue';
import CorporateManagePass from './CorporateManagePass.vue';
import CorporateSheet from './CorporateSheet.vue';
import CorporateStatics from './CorporateStatics.vue';
import CorporateNotice from './CorporateNotice.vue';
import CorporateProfile from './CorporateProfile.vue';

const currentComp = shallowRef(CorporateDashboard);
const changeCurrentComp = comp => (currentComp.value = comp);
</script>

<style lang="scss" scoped></style>
