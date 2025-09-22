export default function Home() {
  return (
    <div className="flex flex-col items-center justify-center min-h-[70vh] text-center">
      <h1 className="text-4xl font-bold mb-4">OrbitLink</h1>
      <p className="text-lg text-gray-600 mb-8">
        인스타 댓글에 반응하는 자동 DM 솔루션 🚀
      </p>
      <a
        href="/login"
        className="px-6 py-3 bg-blue-600 text-white rounded-lg shadow hover:bg-blue-700"
      >
        시작하기
      </a>
    </div>
  );
}
