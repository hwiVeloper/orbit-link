import React from "react";

const Privacy = () => {
  return (
    <div className="p-6 bg-gray-50 text-gray-800">
      <h1 className="text-3xl font-bold mb-4">Orbit Link 개인정보 처리방침</h1>
      <p className="mb-6">
        스튜디오 휘(이하 "회사")는 이용자의 개인정보를 중요하게 생각하며,
        『개인정보 보호법』을 준수하고 있습니다. 본 개인정보 처리방침은 회사가
        제공하는 서비스(이하 "서비스") 이용과 관련하여 개인정보가 어떻게 수집,
        이용, 보관, 보호되는지에 대해 설명합니다.이에 회사는 정보통신망 이용촉진
        및 정보보호 등에 관한 법률, 개인정보보호법 등 정보통신제공자가
        준수하여야 할 관련 법규상의 개인정보보호 규정을 준수하고 있습니다.
      </p>
      <p className="mb-6">
        회사는 아래와 같이 개인정보처리방침을 명시하여 회원이 회사에 제공한
        개인정보가 어떠한 용도와 방식으로 이용되고 있으며 개인정보보호를 위해
        어떠한 조치를 취하는지 알려드립니다.
      </p>
      <p className="mb-6">
        회사의 서비스 개인정보처리방침은 정부의 법률 및 지침의 변경과 당사의
        약관 및 내부 정책에 따라 변경될 수 있으며 이를 개정하는 경우 회사는
        변경사항에 대하여 즉시 서비스 화면에 게시합니다.
      </p>

      <section className="mb-6">
        <h2 className="text-2xl font-semibold mb-2">
          1. 수집하는 개인정보 항목
        </h2>
        <ul className="list-disc pl-6">
          <li>
            <strong>회원가입 시 수집 정보</strong>: (로그인)아이디, 닉네임,
            비밀번호, 이메일 등
          </li>
          <li>
            <strong>서비스 이용 중 수집 정보</strong>: 외부 서비스를 통한 연동
            시 제 3자로부터 다음과 같은 개인 정보를 수집하고 있습니다.
            <ul className="list-disc pl-6">
              <li>Instagram ID, Instagram 이름, 프로필 사진, 이메일 주소</li>
              <li>
                서비스를 통해 발송한 메시지를 수신한 고객의 정보 (Instagram
                이름, Instagram 프로필 이미지의 CDN URL)
              </li>
            </ul>
          </li>
          <li>
            <strong>자동 수집 정보</strong>: PC웹, 모바일 웹/앱 이용 과정에서
            단말기정보(OS, 화면사이즈, 디바이스 아이디, 폰기종, 단말기 모델명),
            IP주소, 쿠키, 방문일시, 부정이용기록, 서비스 이용 기록, 방문자 활동
            기록 등의 정보가 자동으로 생성되어 수집될 수 있습니다. 로그 등
          </li>
          <li>
            <strong>기기 권한 관련 정보</strong>: 카메라 접근 권한, 사진/앨범
            접근 권한 (사진 업로드 및 서비스 제공 목적)
          </li>
          <li>
            <strong>외부 공개 SNS 데이터 수집 시</strong>: 회사는 Instagram
            플랫폼에서 공개된 계정의 일부 정보를 전달받아 Orbit Link 내 서비스
            고도화를 위해 활용합니다. 회사는 해당 데이터를 Orbit Link 내에
            등록된 회원의 계정 정보와 독립적으로 비교/매칭하며, 제3자에게 회원
            정보를 제공하지 않습니다.
            <ul className="list-disc pl-6">
              <li>
                수집 항목: Instagram 계정 ID, 프로필 이름 및 이미지, 게시물
                캡션, 해시태그, 게시일, 좋아요 수, 댓글 수, 팔로워 수, 팔로잉
                수, 노출 수 등
              </li>
              <li>
                활용 목적: 콘텐츠 기반 추천, 검색, 매칭 기능, 또는 Orbit Link
                플랫폼 내 탐색 페이지, 리스트 등에 노출될 수 있음
              </li>
            </ul>
            <p>
              ※ 회사는 공개된 SNS 데이터라 하더라도 개인을 식별할 수 있는
              경우에는 개인정보 보호법에 따라 적절히 보호하며, 정보주체의 삭제
              요청 권리를 보장합니다.
            </p>
            <p>
              개인정보를 수집하는 경우에는 반드시 사전에 이용자에게 해당 사실을
              알리고 동의를 구하고 있으며, 아래와 같은 방법을 통해 개인정보를
              수집합니다.
            </p>
            <ul className="list-disc pl-6">
              <li>
                회원가입 및 서비스 이용 과정에서 이용자가 개인정보 수집에 대해
                동의를 하고 직접 정보를 입력하는 경우
              </li>
              <li>제휴 서비스 또는 단체 등으로부터 개인정보를 제공받은 경우</li>
              <li>
                고객센터를 통한 상담 과정에서 웹페이지, 메일, 팩스, 전화 등
              </li>
              <li>온·오프라인에서 진행되는 이벤트/행사 등 참고</li>
              <li>
                이 중 제3자가 회원으로부터 동의를 받아 서비스에 제공하는
                방식으로 이루어지는 개인정보의 수집에 대하여는 본
                개인정보처리방침이 적용되지 않음을 알려드립니다.
              </li>
            </ul>
          </li>
        </ul>
      </section>

      <section className="mb-6">
        <h2 className="text-2xl font-semibold mb-2">
          2. 개인정보의 수집 및 이용 목적
        </h2>
        <ul className="list-disc pl-6">
          <li>회원가입 및 본인 확인</li>
          <li>서비스 제공 및 운영</li>
          <li>데이터 분석을 통한 서비스 개선</li>
          <li>고객 문의 대응 및 불만 처리</li>
          <li>법적 의무 이행</li>
        </ul>
      </section>

      <section className="mb-6">
        <h2 className="text-2xl font-semibold mb-2">
          3. 개인정보의 보유 및 이용 기간
        </h2>
        <p>
          회사는 이용자의 개인정보를 원칙적으로 회원 탈퇴 시 즉시 삭제합니다.
        </p>
        <p>각각의 개인정보 처리 및 보유 기간은 다음과 같습니다.</p>
        <ul className="list-disc pl-6">
          <li>
            <strong>보존 항목</strong>: 회원 가입 정보 (이메일, 닉네임, 프로필
            이미지)
          </li>
          <li>
            <strong>보존 기간</strong>: 회원 탈퇴 후 1년까지 보관
          </li>
          <li>
            <strong>보존 근거</strong>: 분쟁 해결, 법적 의무 이행 등
          </li>
        </ul>
      </section>

      <section className="mb-6">
        <h2 className="text-2xl font-semibold mb-2">
          4. 개인정보 제3자 제공 및 국외 이전
        </h2>
        <h3 className="text-xl font-medium mb-2">
          (1) 개인정보 처리 위탁 및 국외 이전
        </h3>
        <table className="table-auto border-collapse border border-gray-300 w-full text-left">
          <thead>
            <tr>
              <th className="border border-gray-300 px-4 py-2">제공받는 자</th>
              <th className="border border-gray-300 px-4 py-2">목적</th>
              <th className="border border-gray-300 px-4 py-2">
                이전되는 개인정보 항목
              </th>
              <th className="border border-gray-300 px-4 py-2">보관 국가</th>
            </tr>
          </thead>
          <tbody>
            <tr>
              <td className="border border-gray-300 px-4 py-2">
                AWS (Amazon Web Services)
              </td>
              <td className="border border-gray-300 px-4 py-2">
                데이터 보관 및 클라우드 서비스 제공
              </td>
              <td className="border border-gray-300 px-4 py-2">
                수집된 모든 개인정보
              </td>
              <td className="border border-gray-300 px-4 py-2">미국</td>
            </tr>
            <tr>
              <td className="border border-gray-300 px-4 py-2">Google Inc.</td>
              <td className="border border-gray-300 px-4 py-2">
                Google AdSense, Google Analytics 서비스
              </td>
              <td className="border border-gray-300 px-4 py-2">
                사용하는 브라우저 정보, 거주국, 설정 언어, 기기 모델정보, 서비스
                접속시간/사용이력 등
              </td>
              <td className="border border-gray-300 px-4 py-2">미국</td>
            </tr>
          </tbody>
        </table>
        <p className="mt-4">
          이용자는 개인정보의 국외 이전에 대해 동의를 거부할 수 있으나, 이 경우
          회원가입 및 서비스 이용이 제한될 수 있습니다.
        </p>
      </section>

      <section className="mb-6">
        <h2 className="text-2xl font-semibold mb-2">
          5. 이용자의 권리 및 행사 방법
        </h2>
        <p>
          이용자는 언제든지 자신의 개인정보에 대해 조회, 정정, 삭제 등의 권리를
          행사할 수 있습니다. 이러한 요청은 서면, 이메일 등을 통해 제출할 수
          있으며, 회사는 지체 없이 조치하겠습니다.
        </p>
      </section>

      <section className="mb-6">
        <h2 className="text-2xl font-semibold mb-2">
          6. 개인정보 보호를 위한 보안 조치
        </h2>
        <ul className="list-disc pl-6">
          <li>
            개인정보 암호화: 이메일 및 비밀번호는 암호화되어 저장되며,
            비밀번호는 SHA-256 해시 암호화 적용
          </li>
          <li>접근 통제: 개인정보 접근 권한 최소화 및 보안 시스템 운영</li>
          <li>데이터 보호: 네트워크 보안 강화 및 안전한 서버 운영</li>
          <li>
            이미지 데이터 보호: 개인정보와 마찬가지로 이미지 데이터는 암호화하여
            처리 및 저장됩니다. 이미지 데이터 처리를 위한 서버 및 위탁 업체로
            전송 시 SSL/TLS와 같은 안전한 데이터 전송 프로토콜을 사용하여
            암호화됩니다.
          </li>
        </ul>
      </section>

      <section className="mb-6">
        <h2 className="text-2xl font-semibold mb-2">7. 개인정보 보호 책임자</h2>
        <p>
          개인정보 보호와 관련된 문의 사항은 아래 담당자에게 연락해 주시기
          바랍니다.
        </p>
        <ul className="list-disc pl-6">
          <li>
            <strong>개인정보 보호 책임자</strong>: 이종휘
          </li>
          <li>
            <strong>이메일</strong>: studio.hwi.app@gmail.com
          </li>
        </ul>
      </section>

      <section>
        <h2 className="text-2xl font-semibold mb-2">
          8. 개인정보 처리방침 변경
        </h2>
        <p>
          1) 본 개인정보 처리방침은 법령 및 정책 변경 등에 따라 개정될 수
          있으며, 변경 사항은 서비스 내 공지 또는 이메일을 통해 사전 안내됩니다.
        </p>
        <p>
          2) 회사는 개인정보처리방침의 내용 추가, 삭제 및 수정이 있을 경우 개정
          최소 7일 전에 서비스 내 공지사항을 통해 사전 공지를 할 것입니다.
        </p>
        <p>
          3) 수집하는 개인정보의 항목 등이 변경이 있는 경우, 필요 시 이용자
          동의를 다시 받을 수도 있습니다.
        </p>
        <p className="mt-2">
          <strong>시행일</strong>: 2026년 01월 01일
        </p>
      </section>
    </div>
  );
};

export default Privacy;
