export default function DashboardPage() {
  return (
    <div className="p-8">
      <h1 className="text-3xl font-bold mb-4">대시보드</h1>
      <p className="text-gray-700 mb-6">환영합니다 👋</p>

      <div className="grid grid-cols-3 gap-6">
        <div className="p-6 border rounded-lg shadow">
          <h2 className="font-semibold mb-2">내 구독 상태</h2>
          <p className="text-sm text-gray-600">활성 / 비활성</p>
        </div>
        <div className="p-6 border rounded-lg shadow">
          <h2 className="font-semibold mb-2">최근 결제 내역</h2>
          <p className="text-sm text-gray-600">표시 예정</p>
        </div>
        <div className="p-6 border rounded-lg shadow">
          <h2 className="font-semibold mb-2">내 정보</h2>
          <p className="text-sm text-gray-600">사용자 이름 / 이메일</p>
        </div>
      </div>
    </div>
  );
}
