// import axios from 'axios';
// import { Message, Modal } from '@arco-design/web-vue';
// import { useUserStore } from '@/store';
// import { getToken, getTokenName } from '@/utils/auth';

// if (import.meta.env.VITE_API_BASE_URL) {
//     axios.defaults.baseURL = import.meta.env.VITE_API_BASE_URL;
// }
// axios.interceptors.request.use((config) => {
//     const token = getToken();
//     const tokenName = getTokenName() || "token";
//     if (token) {
//         if (!config.headers) {
//             config.headers = {};
//         }
//         config.headers[tokenName] = token;
//     }
//     return config;
// }, (error) => {
//     // do something
//     return Promise.reject(error);
// });
// // add response interceptors
// axios.interceptors.response.use((response) => {
//     const { data, status, statusText } = response;
//     if (status != 200) {
//         Message.error({
//             content: `出现异常信息, 异常代码:${status}, 异常说明:${statusText}`,
//             duration: 2000
//         });
//         return null;
//     }
//     const meta = data.meta;
//     if (meta.code === 200) {
//         return data;
//     }
//     switch (meta.code) {
//         case 1000: // 没有传入Token
//         case 1001: // Token过期
//             Modal.error({
//                 maskClosable: false,
//                 title: '重新登录',
//                 content: '您已登出，您可以取消留在此页面，或重新登录',
//                 okText: '重新登录',
//                 async onOk() {
//                     const userStore = useUserStore();
//                     await userStore.logout();
//                     window.location.reload();
//                 },
//             });
//             break;
//         case 1002:
//             Message.error({
//                 content: `无权限操作`,
//                 duration: 2000
//             });
//             // Modal.error({
//             //   maskClosable: false,
//             //   title: '权限校验',
//             //   content:
//             //     '您暂无当前权限，请联系管理员',
//             //   okText: '退出',
//             //   async onOk() {
//             //     const userStore = useUserStore();
//             //     await userStore.logout();
//             //     window.location.reload();
//             //   },
//             // });
//             break;
//         default:
//             Message.error({
//                 content: `出现异常信息, 异常代码:${meta.code}, 异常说明:【${meta.message}】`,
//                 duration: 2000
//             });
//             break;
//     }
//     return Promise.reject(new Error(meta.message || 'Error'));
// }, (error) => {
//     Message.error({
//         content: error.msg || 'Request Error',
//         duration: 2000,
//     });
//     return Promise.reject(error);
// });

import axios from 'axios';
import type { AxiosRequestConfig, AxiosResponse } from 'axios';
import { Message, Modal } from '@arco-design/web-vue';
import { useUserStore } from '@/store';
import { getToken } from '@/utils/auth';

export interface HttpResponse<T = unknown> {
  status: number;
  msg: string;
  code: number;
  data: T;
}

if (import.meta.env.VITE_API_BASE_URL) {
  axios.defaults.baseURL = import.meta.env.VITE_API_BASE_URL;
}

axios.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    // let each request carry token
    // this example using the JWT token
    // Authorization is a custom headers key
    // please modify it according to the actual situation
    const token = getToken();
    if (token) {
      if (!config.headers) {
        config.headers = {};
      }
      config.headers.Authorization = `Bearer ${token}`;
    }
    return config;
  },
  (error) => {
    // do something
    return Promise.reject(error);
  }
);
// add response interceptors
axios.interceptors.response.use(
  (response: AxiosResponse<HttpResponse>) => {
    const res = response.data;
    // if the custom code is not 20000, it is judged as an error.
    if (res.code !== 20000) {
      Message.error({
        content: res.msg || 'Error',
        duration: 5 * 1000,
      });
      // 50008: Illegal token; 50012: Other clients logged in; 50014: Token expired;
      if (
        [50008, 50012, 50014].includes(res.code) &&
        response.config.url !== '/api/user/info'
      ) {
        Modal.error({
          title: 'Confirm logout',
          content:
            'You have been logged out, you can cancel to stay on this page, or log in again',
          okText: 'Re-Login',
          async onOk() {
            const userStore = useUserStore();

            await userStore.logout();
            window.location.reload();
          },
        });
      }
      return Promise.reject(new Error(res.msg || 'Error'));
    }
    return res;
  },
  (error) => {
    Message.error({
      content: error.msg || 'Request Error',
      duration: 5 * 1000,
    });
    return Promise.reject(error);
  }
);
