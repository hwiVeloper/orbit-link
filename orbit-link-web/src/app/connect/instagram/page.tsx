"use client";

export default function LoginPage() {
  const clientId = process.env.NEXT_PUBLIC_FACEBOOK_APP_ID!;
  const redirectUri = process.env.NEXT_PUBLIC_FACEBOOK_REDIRECT_URI!;
  const scopes = "pages_show_list,instagram_basic,instagram_manage_messages"; // 필요한 권한

  const loginUrl = `https://www.facebook.com/v21.0/dialog/oauth?client_id=${clientId}&redirect_uri=${encodeURIComponent(
    redirectUri
  )}&scope=${encodeURIComponent(scopes)}`;

  return (
    <div className="flex flex-col items-center justify-center min-h-[80vh]">
      <h1 className="text-2xl font-bold mb-6">Instagram 연동</h1>
      <a
        href={loginUrl}
        className="px-6 py-3 bg-blue-600 text-white rounded-lg shadow hover:bg-blue-700"
      >
        Instagram 계정 연결하기
      </a>
    </div>
  );
}
