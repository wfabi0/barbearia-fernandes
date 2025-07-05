import type { Metadata } from "next";
import Body from "../Components/Body/Body";
import "./globals.css";



export const metadata: Metadata = {
  title: "Barbearia Fernandes",
};

export default function RootLayout({ children, }: Readonly<{ children: React.ReactNode; }>) {

  return (
    <html lang="pt-BR">
      <Body>
        {children}
      </Body>

    </html>
  );
}
