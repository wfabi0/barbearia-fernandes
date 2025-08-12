import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Entre em contato com o nosso Suporte",
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