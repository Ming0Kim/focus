<template>
  <div>
    <div class="flex flex-col space-y-10">
      <!-- 
    # 네이버님 안녕하세요
    -->
      <nav class="flex flex-wrap justify-between p-8 text-gray-800">
        <h1 class="font-bold">네이버 님, 안녕하세요</h1>
        <h3 class="font-bold text-gray-500">DASHBOARD</h3>
      </nav>
      <div class="flex flex-col">
        <div class="flex flex-col justify-center">
          <div class="inline-block w-auto py-2 sm:px-6 lg:px-8 space-y-4">
            <div class="flex flex-wrap justify-between px-5">
              <p class="font-medium text-2xl">공지 게시판</p>
              <div class="flex justify-center space-x-2">
                <button
                  type="button"
                  class="inline-block rounded bg-blue-600 px-6 py-2.5 text-md font-medium uppercase leading-tight text-white shadow-md transition duration-150 ease-in-out hover:bg-blue-700 hover:shadow-lg focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg"
                >
                  공지 작성
                </button>
              </div>
            </div>
            <div class="overflow-hidden rounded-md shadow-lg">
              <table class="min-w-full">
                <thead class="border-b bg-white">
                  <tr>
                    <th
                      scope="col"
                      class="px-6 py-4 text-left text-sm font-medium text-gray-900"
                    >
                      카테고리
                    </th>
                    <th
                      scope="col"
                      class="px-6 py-4 text-left text-sm font-medium text-gray-900"
                    >
                      공지명
                    </th>
                    <th
                      scope="col"
                      class="px-6 py-4 text-left text-sm font-medium text-gray-900"
                    >
                      날짜
                    </th>
                  </tr>
                </thead>
                <tbody>
                  <tr
                    class="border-b bg-white transition duration-300 ease-in-out hover:bg-gray-100"
                  >
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-medium text-gray-900"
                    >
                      1
                    </td>

                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      v.1234으로 업데이트
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      2022.04.06
                    </td>
                  </tr>
                  <tr
                    class="border-b bg-white transition duration-300 ease-in-out hover:bg-gray-100"
                  >
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-medium text-gray-900"
                    >
                      1
                    </td>

                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      v.1234으로 업데이트
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      2022.04.06
                    </td>
                  </tr>
                  <tr
                    class="border-b bg-white transition duration-300 ease-in-out hover:bg-gray-100"
                  >
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-medium text-gray-900"
                    >
                      1
                    </td>

                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      v.1234으로 업데이트
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      2022.04.06
                    </td>
                  </tr>
                  <tr
                    class="border-b bg-white transition duration-300 ease-in-out hover:bg-gray-100"
                  >
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-medium text-gray-900"
                    >
                      1
                    </td>

                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      v.1234으로 업데이트
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      2022.04.06
                    </td>
                  </tr>
                  <tr
                    class="border-b bg-white transition duration-300 ease-in-out hover:bg-gray-100"
                  >
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-medium text-gray-900"
                    >
                      1
                    </td>

                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      v.1234으로 업데이트
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      2022.04.06
                    </td>
                  </tr>
                  <tr
                    class="border-b bg-white transition duration-300 ease-in-out hover:bg-gray-100"
                  >
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-medium text-gray-900"
                    >
                      1
                    </td>

                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      v.1234으로 업데이트
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      2022.04.06
                    </td>
                  </tr>
                  <tr
                    class="border-b bg-white transition duration-300 ease-in-out hover:bg-gray-100"
                  >
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-medium text-gray-900"
                    >
                      1
                    </td>

                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      v.1234으로 업데이트
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      2022.04.06
                    </td>
                  </tr>
                  <tr
                    class="border-b bg-white transition duration-300 ease-in-out hover:bg-gray-100"
                  >
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-medium text-gray-900"
                    >
                      1
                    </td>

                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      v.1234으로 업데이트
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      2022.04.06
                    </td>
                  </tr>
                  <tr
                    class="border-b bg-white transition duration-300 ease-in-out hover:bg-gray-100"
                  >
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-medium text-gray-900"
                    >
                      1
                    </td>

                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      v.1234으로 업데이트
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      2022.04.06
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';

const notices = ref([]);

onMounted(() => {
  axios
    .get('http://localhost:3000/notices')
    .then(({ data }) => {
      notices.value = data.notices.notices;
      console.log(notices.value);
    })
    .catch(err => {
      console.log(err.message);
    });
});
</script>

<style lang="scss" scoped></style>
