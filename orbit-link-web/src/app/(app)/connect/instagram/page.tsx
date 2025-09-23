"use client";

const ConnectInstagramPage = () => {
  const clientId = process.env.NEXT_PUBLIC_FACEBOOK_APP_ID!;
  const redirectUri = process.env.NEXT_PUBLIC_FACEBOOK_REDIRECT_URI!;
  // 필요한 권한
  const scopes =
    "instagram_business_basic,instagram_business_content_publish,instagram_business_manage_messages,instagram_business_manage_comments";

  const loginUrl = `https://www.instagram.com/oauth/authorize?client_id=${clientId}&redirect_uri=${encodeURIComponent(
    redirectUri
  )}&response_type=code&scope=${encodeURIComponent(scopes)}`;

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
};

export default ConnectInstagramPage;
