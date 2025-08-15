import type { Metadata } from "next";

export const metadata: Metadata = {
  title: "Acesso Negado - Barbearia Fernandes",
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