## goku-framework
![logo](./doc/logo.png)

### intro

SpringBoot era greatly simplifies the building of the whole system, and ** Goku-Framework ** can deal with the problem of the whole development process faster.

Existence as an infrastructure layer:

- Strict control over dependent versions and custom versions for overrides

- Network development based on Netty, divided into single machine version and cluster version, standardized game architecture, simplified processing of difficult business

- How log information is stored is up to you, CRUD is too fast to adapt

- Built-in Activiti visualization flow chart, distributed ID, Binlog parsing and other solutions.

- ...

### framework

```text
├─goku-framework-dependency                           // dependency
├─goku-framework-socket                               // Netty          
├─goku-framework-starter                              //                   
│  ├─goku-framework-starter-cache                     // Redis                    
│  ├─goku-framework-starter-log                       // log                  
│  ├─goku-framework-starter-material                  // MinIO，阿里OSS                        
│  ├─goku-framework-starter-mybatis                   // CRUD                  
│  └─goku-framework-starter-web                       // SpringBoot Develop 
├─goku-framework-support                              //               
│  ├─goku-framework-support-activiti                  // Activiti         
│  ├─goku-framework-support-mysql-binlog              // MySQL binlog       
│  └─goku-framework-support-primary                   // A distributed ids             
├─goku-framework-tools                                // core          
```

### use

Focus on sample projects about how the overall architecture is used

- `square-test-spring-boot-starter`

For more details, follow the blog:

- [Mr.Xie【Mr.Xie】的掘金](https://juejin.cn/user/3359725700263694)
- [Mr.Xie【Mr.Xie】的51CTO](https://blog.51cto.com/u_14948012)

> Based on 'goku-framework' framework [enjoy reading II](https://github.com/xiezhyan/enjoy-read-ii) is hot research and development!!

### Special offer

If you use EasyCode, the code generation configuration based on 'Goku-framework' can help you

[EasyCode Config](./doc/EasyCodeConfig.json)

