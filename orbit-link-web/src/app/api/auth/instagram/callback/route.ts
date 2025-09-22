import { NextRequest, NextResponse } from "next/server";

export async function GET(req: NextRequest) {
  const code = req.nextUrl.searchParams.get("code");

  if (!code) {
    return NextResponse.json({ error: "No code provided" }, { status: 400 });
  }

  // Spring Boot 서버로 전달
  const backendUrl =
    process.env.BACKEND_BASE_URL + "/api/auth/instagram/callback";
  const res = await fetch(backendUrl + `?code=${code}`, { method: "GET" });

  if (!res.ok) {
    return NextResponse.json(
      { error: "Failed to exchange code" },
      { status: 500 }
    );
  }

  return NextResponse.redirect("/dashboard");
}
