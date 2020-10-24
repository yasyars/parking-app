-- phpMyAdmin SQL Dump
-- version 5.0.3
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Waktu pembuatan: 24 Okt 2020 pada 10.27
-- Versi server: 10.4.14-MariaDB
-- Versi PHP: 7.3.23

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `parking_user`
--

-- --------------------------------------------------------

--
-- Struktur dari tabel `area_parkir`
--

CREATE TABLE `area_parkir` (
  `id_area` varchar(45) NOT NULL,
  `nama_area` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `garage`
--

CREATE TABLE `garage` (
  `id_garage` varchar(45) NOT NULL,
  `nama_garage` varchar(45) NOT NULL,
  `tarif` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `kendaraan`
--

CREATE TABLE `kendaraan` (
  `plat_no` varchar(45) NOT NULL,
  `tipe_kendaraan` varchar(45) NOT NULL,
  `jenis_kendaraan` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `subscription`
--

CREATE TABLE `subscription` (
  `id_sub` varchar(45) NOT NULL,
  `jenis_sub` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `tarif`
--

CREATE TABLE `tarif` (
  `id_tarif` varchar(11) NOT NULL,
  `harga` int(11) NOT NULL,
  `jenis_kendaraan` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `transaksi_parkir`
--

CREATE TABLE `transaksi_parkir` (
  `id_transaksi` varchar(45) NOT NULL,
  `id_user` int(11) NOT NULL,
  `id_parkir` varchar(45) NOT NULL,
  `id_sub` varchar(45) NOT NULL,
  `plat_no` varchar(45) NOT NULL,
  `id_garage` varchar(45) NOT NULL,
  `waktu_masuk` datetime NOT NULL,
  `waktu_keluar` datetime NOT NULL,
  `durasi` time NOT NULL,
  `total_harga` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Struktur dari tabel `user`
--

CREATE TABLE `user` (
  `id_user` int(11) NOT NULL,
  `nama` varchar(45) DEFAULT NULL,
  `alamat` varchar(45) DEFAULT NULL,
  `email` varchar(45) DEFAULT NULL,
  `is_admin` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Indexes for dumped tables
--

--
-- Indeks untuk tabel `area_parkir`
--
ALTER TABLE `area_parkir`
  ADD PRIMARY KEY (`id_area`);

--
-- Indeks untuk tabel `garage`
--
ALTER TABLE `garage`
  ADD PRIMARY KEY (`id_garage`);

--
-- Indeks untuk tabel `kendaraan`
--
ALTER TABLE `kendaraan`
  ADD PRIMARY KEY (`plat_no`);

--
-- Indeks untuk tabel `subscription`
--
ALTER TABLE `subscription`
  ADD PRIMARY KEY (`id_sub`);

--
-- Indeks untuk tabel `tarif`
--
ALTER TABLE `tarif`
  ADD PRIMARY KEY (`id_tarif`);

--
-- Indeks untuk tabel `transaksi_parkir`
--
ALTER TABLE `transaksi_parkir`
  ADD PRIMARY KEY (`id_transaksi`),
  ADD KEY `id_user` (`id_user`,`id_parkir`,`id_sub`,`plat_no`,`id_garage`),
  ADD KEY `id_garage` (`id_garage`),
  ADD KEY `id_sub` (`id_sub`),
  ADD KEY `id_parkir` (`id_parkir`),
  ADD KEY `plat_no` (`plat_no`);

--
-- Indeks untuk tabel `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id_user`);

--
-- AUTO_INCREMENT untuk tabel yang dibuang
--

--
-- AUTO_INCREMENT untuk tabel `user`
--
ALTER TABLE `user`
  MODIFY `id_user` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
