import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Login - Barbearia Fernandes",
};

export default function RootLayout({
  children,
}: Readonly<{
  children: React.ReactNode;
}>) {
  return (
    <>
     {children}
    </>
  );
}