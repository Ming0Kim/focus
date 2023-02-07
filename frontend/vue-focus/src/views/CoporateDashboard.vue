<template>
  <div class="flex flex-col space-y-10">
    <!-- 
    # 네이버님 안녕하세요
    -->
    <nav class="flex flex-wrap justify-between p-8 text-gray-800">
      <h1 class="font-bold">네이버 님, 안녕하세요</h1>
      <h3 class="font-bold text-gray-500">DASHBOARD</h3>
    </nav>
    <!-- 
# 현재 인원
 -->
    <div class="flex space-x-2 justify-center">
      <div
        class="bg-white shadow-lg mx-auto w-2/5 max-w-full text-sm pointer-events-auto bg-clip-padding rounded-lg block"
      >
        <div
          class="bg-white flex justify-between items-center py-2 px-3 bg-clip-padding border-b border-gray-200 rounded-t-lg"
        >
          <p class="font-bold text-gray-500">현재 인원</p>
        </div>
        <!--         
        # 총 평가자 총 지원자
        -->
        <div
          class="p-3 text-lg bg-white rounded-b-lg break-words text-gray-700 text-center"
        >
          총 평가자 13 | 총 지원자 39
        </div>
      </div>
    </div>
    <!-- 
    # 면접진행률
 -->
    <!-- <div class="flex space-x-2 justify-center">
      <div
        class="bg-white shadow-lg mx-auto w-2/5 max-w-full text-sm pointer-events-auto bg-clip-padding rounded-lg block"
      >
        <div
          class="bg-white flex justify-between items-center py-2 px-3 bg-clip-padding border-b border-gray-200 rounded-t-lg"
        >
          <p class="font-bold text-gray-500">면접 진행률</p>
        </div>
        <div class="p-5">
          <div class="w-full bg-gray-200 rounded-full">
            <div
              class="bg-blue-600 text-xs font-medium text-blue-100 text-center p-0.5 leading-none rounded-l-full"
              style="width: 25%"
            >
              25%
            </div>
          </div>
        </div>
      </div>
    </div> -->
    <!--     
    # 예정된 면접
 -->
    <div class="flex space-x-2 justify-center">
      <div
        class="bg-white shadow-lg mx-auto w-2/5 max-w-full text-sm pointer-events-auto bg-clip-padding rounded-lg block"
      >
        <div
          class="bg-white flex justify-between items-center py-2 px-3 bg-clip-padding border-b border-gray-200 rounded-t-lg"
        >
          <p class="font-bold text-gray-500">예정된 면접</p>
        </div>
        <!--         
        # 면접리스트
        -->
        <ul>
          <!-- # 면접리스트 1 -->
          <li
            class="p-3 text-lg bg-white rounded-b-lg break-words text-gray-700"
          >
            <section class="flex flex-row px-2">
              <div class="flex flex-col space-y-2 w-1/2 justify-center">
                <p class="text-xl font-bold">두나무 증권 FE 개발자</p>
              </div>
              <ul class="flex flex-col w-auto space-y-4">
                <!-- 
              # 리스트 component
              -->
                <li class="flex flex-row items-center">
                  <div
                    class="bg-blue-500 w-5 h-5 ring-1 ring-slate-900/5 shadow-lg rounded-full flex items-center justify-center"
                  >
                    <i class="bx bx-right-arrow-alt text-white"></i>
                  </div>
                  <div class="px-2">
                    <p>2022.02.04 ~ 2022.02.05</p>
                  </div>
                </li>
                <li class="flex flex-row items-center">
                  <div
                    class="animate-bounce bg-blue-500 w-5 h-5 ring-1 ring-slate-900/5 shadow-lg rounded-full flex items-center justify-center"
                  >
                    <i class="bx bx-right-arrow-alt text-white"></i>
                  </div>
                  <div class="px-2">
                    <p>2022.02.04 ~ 2022.02.05</p>
                  </div>
                </li>
                <li class="flex flex-row items-center">
                  <div
                    class="bg-blue-500 w-5 h-5 ring-1 ring-slate-900/5 shadow-lg rounded-full flex items-center justify-center"
                  >
                    <i class="bx bx-right-arrow-alt text-white"></i>
                  </div>
                  <div class="px-2">
                    <p>2022.02.04 ~ 2022.02.05</p>
                  </div>
                </li>
                <!-- 
              #리스트 component end
              --></ul>
            </section>
          </li>
          <hr />
          <li
            class="p-3 text-lg bg-white rounded-b-lg break-words text-gray-700"
          >
            <section class="flex flex-row px-2">
              <div class="flex flex-col space-y-2 w-1/2 justify-center">
                <p class="text-xl font-bold">두나무 증권 FE 개발자</p>
              </div>
              <ul class="flex flex-col w-auto space-y-4">
                <!-- 
              # 리스트 component
              -->
                <li class="flex flex-row items-center">
                  <div
                    class="bg-blue-500 w-5 h-5 ring-1 ring-slate-900/5 shadow-lg rounded-full flex items-center justify-center"
                  >
                    <i class="bx bx-right-arrow-alt text-white"></i>
                  </div>
                  <div class="px-2">
                    <p>2022.02.04 ~ 2022.02.05</p>
                  </div>
                </li>
                <li class="flex flex-row items-center">
                  <div
                    class="animate-bounce bg-blue-500 w-5 h-5 ring-1 ring-slate-900/5 shadow-lg rounded-full flex items-center justify-center"
                  >
                    <i class="bx bx-right-arrow-alt text-white"></i>
                  </div>
                  <div class="px-2">
                    <p>2022.02.04 ~ 2022.02.05</p>
                  </div>
                </li>
                <li class="flex flex-row items-center">
                  <div
                    class="bg-blue-500 w-5 h-5 ring-1 ring-slate-900/5 shadow-lg rounded-full flex items-center justify-center"
                  >
                    <i class="bx bx-right-arrow-alt text-white"></i>
                  </div>
                  <div class="px-2">
                    <p>2022.02.04 ~ 2022.02.05</p>
                  </div>
                </li>
                <!-- 
              #리스트 component end
              --></ul>
            </section>
          </li>
        </ul>
        <!-- #리스트 끝 -->
      </div>
    </div>
  </div>
</template>

<script setup></script>

<style lang="scss" scoped></style>
