import "@/app/globals.css";

export default function AuthLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return <section className="flex-1">{children}</section>;
}
