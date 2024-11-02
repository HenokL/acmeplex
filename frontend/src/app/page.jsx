import Image from "next/image";
import Link from "next/link";

export default function Home() {
  return (
    <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 gap-16 sm:p-20">
      <header className="flex justify-between items-center w-full">
        <h1 className="text-2xl font-bold">Movie Reservation System</h1>
        <nav>
          <Link href="/movies" className="mr-4 text-lg">Movies</Link>
          <Link href="/reservation" className="text-lg">Reservation</Link>
        </nav>
      </header>
      
      <main className="flex flex-col gap-8 items-center sm:items-start">
        <Image
          className="dark:invert"
          src="/images/logo.svg" // Placeholder for your logo
          alt="Movie Reservation System logo"
          width={180}
          height={38}
          priority
        />
        <h2 className="text-xl">Welcome to the Movie Reservation System</h2>
        <p className="text-center">Easily browse movies and make reservations!</p>
        
        <div className="flex gap-4 items-center flex-col sm:flex-row">
          <Link
            className="rounded-full border border-solid border-transparent transition-colors flex items-center justify-center bg-blue-500 text-white gap-2 hover:bg-blue-700 text-sm sm:text-base h-10 sm:h-12 px-4 sm:px-5"
            href="/movies"
          >
            View Movies
          </Link>
          <Link
            className="rounded-full border border-solid border-blue-500 transition-colors flex items-center justify-center text-blue-500 hover:bg-blue-200 text-sm sm:text-base h-10 sm:h-12 px-4 sm:px-5"
            href="/reservation"
          >
            Make a Reservation
          </Link>
          <Link
            className="rounded-full border border-solid border-transparent transition-colors flex items-center justify-center bg-green-500 text-white gap-2 hover:bg-green-700 text-sm sm:text-base h-10 sm:h-12 px-4 sm:px-5"
            href="/login"
          >
            Login
          </Link>
        </div>
      </main>

      <footer className="flex gap-6 flex-wrap items-center justify-center">
        <a
          className="flex items-center gap-2 hover:underline"
          href="https://nextjs.org/docs"
          target="_blank"
          rel="noopener noreferrer"
        >
          Learn more about Next.js
        </a>
      </footer>
    </div>
  );
}
