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
              <p class="font-medium text-2xl">계정 리스트 조회</p>
              <div class="flex justify-center space-x-2">
                <button
                  type="button"
                  class="inline-block rounded bg-blue-600 px-6 py-2.5 text-md font-medium uppercase leading-tight text-white shadow-md transition duration-150 ease-in-out hover:bg-blue-700 hover:shadow-lg focus:bg-blue-700 focus:shadow-lg focus:outline-none focus:ring-0 active:bg-blue-800 active:shadow-lg"
                >
                  계정 생성
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
                    ></th>
                    <th
                      scope="col"
                      class="px-6 py-4 text-left text-sm font-medium text-gray-900"
                    ></th>
                    <th
                      scope="col"
                      class="px-6 py-4 text-left text-sm font-medium text-gray-900"
                    >
                      기업명
                    </th>
                    <th
                      scope="col"
                      class="px-6 py-4 text-left text-sm font-medium text-gray-900"
                    >
                      계약기간
                    </th>
                    <th
                      scope="col"
                      class="px-6 py-4 text-left text-sm font-medium text-gray-900"
                    >
                      담당자
                    </th>
                    <th
                      scope="col"
                      class="px-6 py-4 text-left text-sm font-medium text-gray-900"
                    >
                      ID
                    </th>
                    <th
                      scope="col"
                      class="px-6 py-4 text-left text-sm font-medium text-gray-900"
                    >
                      연락처
                    </th>
                    <th
                      scope="col"
                      class="px-6 py-4 text-left text-sm font-medium text-gray-900"
                    >
                      이메일
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
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 488 512"
                        class="h-7 w-7"
                        style="color: #ea4335"
                      >
                        <!--! Font Awesome Pro 6.0.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2022 Fonticons, Inc. -->
                        <path
                          fill="currentColor"
                          d="M488 261.8C488 403.3 391.1 504 248 504 110.8 504 0 393.2 0 256S110.8 8 248 8c66.8 0 123 24.5 166.3 64.9l-67.5 64.9C258.5 52.6 94.3 116.6 94.3 256c0 86.5 69.1 156.6 153.7 156.6 98.2 0 135-70.4 140.8-106.9H248v-85.3h236.1c2.3 12.7 3.9 24.9 3.9 41.4z"
                        />
                      </svg>
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      Otto
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      2022.04.06 ~ 2022.04.04
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      김민경
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      minofficial
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      010-7337-3460
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      minofficial13@gmail.com
                    </td>
                  </tr>
                  <tr
                    class="border-b bg-white transition duration-300 ease-in-out hover:bg-gray-100"
                  >
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-medium text-gray-900"
                    >
                      2
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 488 512"
                        class="h-7 w-7"
                        style="color: #ea4335"
                      >
                        <!--! Font Awesome Pro 6.0.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2022 Fonticons, Inc. -->
                        <path
                          fill="currentColor"
                          d="M488 261.8C488 403.3 391.1 504 248 504 110.8 504 0 393.2 0 256S110.8 8 248 8c66.8 0 123 24.5 166.3 64.9l-67.5 64.9C258.5 52.6 94.3 116.6 94.3 256c0 86.5 69.1 156.6 153.7 156.6 98.2 0 135-70.4 140.8-106.9H248v-85.3h236.1c2.3 12.7 3.9 24.9 3.9 41.4z"
                        />
                      </svg>
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      Thornton
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      2022.04.06 ~ 2022.04.04
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      김민경
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      minofficial
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      010-7337-3460
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      minofficial13@gmail.com
                    </td>
                  </tr>
                  <tr
                    class="border-b bg-white transition duration-300 ease-in-out hover:bg-gray-100"
                  >
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-medium text-gray-900"
                    >
                      3
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        viewBox="0 0 488 512"
                        class="h-7 w-7"
                        style="color: #ea4335"
                      >
                        <!--! Font Awesome Pro 6.0.0 by @fontawesome - https://fontawesome.com License - https://fontawesome.com/license (Commercial License) Copyright 2022 Fonticons, Inc. -->
                        <path
                          fill="currentColor"
                          d="M488 261.8C488 403.3 391.1 504 248 504 110.8 504 0 393.2 0 256S110.8 8 248 8c66.8 0 123 24.5 166.3 64.9l-67.5 64.9C258.5 52.6 94.3 116.6 94.3 256c0 86.5 69.1 156.6 153.7 156.6 98.2 0 135-70.4 140.8-106.9H248v-85.3h236.1c2.3 12.7 3.9 24.9 3.9 41.4z"
                        />
                      </svg>
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      Wild
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      2022.04.06 ~ 2022.04.04
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      김민경
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      minofficial
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      010-7337-3460
                    </td>
                    <td
                      class="whitespace-nowrap px-6 py-4 text-sm font-light text-gray-900"
                    >
                      minofficial13@gmail.com
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

<script setup></script>

<style lang="scss" scoped></style>
