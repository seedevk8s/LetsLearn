package com.course.learn.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {

    @GetMapping("/")
    @ResponseBody
    public String home() {
        return """
                <html>
                <head>
                    <title>LetsLearn - 온라인 강의 플랫폼</title>
                    <style>
                        body {
                            font-family: -apple-system, BlinkMacSystemFont, 'Segoe UI', sans-serif;
                            max-width: 800px;
                            margin: 50px auto;
                            padding: 20px;
                            background: #f5f5f5;
                        }
                        .container {
                            background: white;
                            padding: 40px;
                            border-radius: 8px;
                            box-shadow: 0 2px 4px rgba(0,0,0,0.1);
                        }
                        h1 {
                            color: #0ea5e9;
                            margin-bottom: 10px;
                        }
                        .subtitle {
                            color: #6b7280;
                            margin-bottom: 30px;
                        }
                        .status {
                            background: #dcfce7;
                            color: #166534;
                            padding: 12px 20px;
                            border-radius: 6px;
                            margin: 20px 0;
                            border-left: 4px solid #16a34a;
                        }
                        .links {
                            margin-top: 30px;
                        }
                        .links a {
                            display: inline-block;
                            margin-right: 15px;
                            color: #0ea5e9;
                            text-decoration: none;
                            padding: 8px 16px;
                            border: 1px solid #0ea5e9;
                            border-radius: 4px;
                            transition: all 0.2s;
                        }
                        .links a:hover {
                            background: #0ea5e9;
                            color: white;
                        }
                        .info {
                            margin-top: 30px;
                            padding: 20px;
                            background: #f0f9ff;
                            border-radius: 6px;
                        }
                        .info h3 {
                            margin-top: 0;
                            color: #0369a1;
                        }
                        .info ul {
                            margin: 10px 0;
                            padding-left: 20px;
                        }
                        .info li {
                            margin: 8px 0;
                            color: #374151;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h1>🎓 LetsLearn</h1>
                        <p class="subtitle">온라인 강의 플랫폼 - 개발 중</p>

                        <div class="status">
                            ✅ Spring Boot 애플리케이션이 정상적으로 실행되었습니다!
                        </div>

                        <div class="info">
                            <h3>📋 프로젝트 정보</h3>
                            <ul>
                                <li><strong>프로젝트명:</strong> LetsLearn</li>
                                <li><strong>Spring Boot:</strong> 3.5.7</li>
                                <li><strong>Java:</strong> 17</li>
                                <li><strong>데이터베이스:</strong> MySQL 8.0</li>
                                <li><strong>현재 단계:</strong> Phase 1 - MVP 개발</li>
                            </ul>
                        </div>

                        <div class="info">
                            <h3>🚀 다음 단계</h3>
                            <ul>
                                <li>도메인 모델 설계 (Entity 클래스 작성)</li>
                                <li>REST API 엔드포인트 구현</li>
                                <li>프론트엔드 개발 (React/Next.js)</li>
                            </ul>
                        </div>

                        <div class="links">
                            <a href="/api/health">Health Check</a>
                            <a href="https://github.com/seedevk8s/LetsLearn" target="_blank">GitHub</a>
                        </div>
                    </div>
                </body>
                </html>
                """;
    }

    @GetMapping("/api/health")
    @ResponseBody
    public String health() {
        return """
                {
                    "status": "UP",
                    "application": "LetsLearn",
                    "version": "0.0.1-SNAPSHOT",
                    "timestamp": "%s"
                }
                """.formatted(java.time.Instant.now());
    }
}
