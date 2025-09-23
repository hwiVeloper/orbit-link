"use client";

export default function LoginPage() {
  const handleGoogleLogin = () => {
    // TODO: /api/auth/google 호출 → 백엔드 OAuth 연동
    window.location.href = "/api/auth/google";
  };

  return (
    <div className="flex flex-col items-center justify-center min-h-[70vh]">
      <h2 className="text-2xl font-bold mb-6">로그인</h2>
      <button
        onClick={handleGoogleLogin}
        className="flex items-center gap-2 px-6 py-3 bg-red-500 text-white rounded-lg shadow hover:bg-red-600"
      >
        <img src="/google-logo.png" alt="Google" className="w-5 h-5" />
        Google 계정으로 로그인
      </button>
    </div>
  );
}
